package com.glcl.backend.service;

import com.glcl.backend.Entity.ClassroomEntity;
import com.glcl.backend.Entity.UserEntity;
import com.glcl.backend.model.AddDeleteUserModel;
import com.glcl.backend.model.ClassroomCreateModel;
import com.glcl.backend.model.JoinByCodeModel;
import com.glcl.backend.model.LeaveClassroomModel;
import com.glcl.backend.repository.ClassroomRepository;
import com.glcl.backend.repository.UserRepository;
import com.glcl.backend.utils.ClassroomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ClassroomService {
  private final ClassroomRepository classroomRepository;
  private final UserRepository userRepository;
  private final ClassroomUtils classroomUtils;

  public ResponseEntity<Object> createClass(ClassroomCreateModel classroomModel) {
    try {
      Optional<UserEntity> userEntityOptional = userRepository.findByEmail(classroomModel.getTeacherName());
      assert userEntityOptional.isPresent();
      UserEntity userEntity = userEntityOptional.get();
      List<UserEntity> teacher = new ArrayList<>();
      teacher.add(userEntity);
      ClassroomEntity classroomEntity = ClassroomEntity.builder()
          .classroomName(classroomModel.getClassroomName())
          .teachers(teacher)
          .code(classroomUtils.codeGenerate())
          .creator(userEntity)
          .build();
      classroomRepository.save(classroomEntity);
      ClassroomEntity tempClassroomEntity = classroomRepository.findByClassroomName(classroomModel.getClassroomName());
      List<ClassroomEntity> classroomEntities = userEntity.getClassroom();
      classroomEntities.add(tempClassroomEntity);
      userEntity.setClassroom(classroomEntities);
      userRepository.save(userEntity);
      return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Classroom created successfully"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Map.of("message", "Error creating classroom"));
    }
  }

  public ResponseEntity<Object> addUser(AddDeleteUserModel addDeleteUserModel) {
    try {
      int flag = 0;
      Optional<ClassroomEntity> classroomEntityOptional = classroomRepository
          .findById(addDeleteUserModel.getClassroomId());
      ClassroomEntity classroomEntity = new ClassroomEntity();
      if (classroomEntityOptional.isPresent()) {
        classroomEntity = classroomEntityOptional.get();
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
      }
      Iterator<String> iterator1 = addDeleteUserModel.getEmail().iterator();
      List<UserEntity> users = new ArrayList<>();
      while (iterator1.hasNext()) {
        String email = iterator1.next();
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
          UserEntity userEntity = userEntityOptional.get();
          List<ClassroomEntity> temp = new ArrayList<>(userEntity.getClassroom());
          if (!temp.contains(classroomEntity)) {
            users.add(userEntityOptional.get());
          } else {
            flag = 1;
            break;
          }
        } else {
          flag = 2;
          break;
        }
      }
      if (flag == 1) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Couldn't add because user/s already exist/s in classroom"));
      } else if (flag == 2) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "User/s don't exist"));
      }
      List<UserEntity> prevUsers = new ArrayList<>();
      if (addDeleteUserModel.getType().equalsIgnoreCase("student")) {
        prevUsers = classroomEntity.getStudents();
        prevUsers.addAll(users);
        classroomEntity.setStudents(prevUsers);
        classroomRepository.save(classroomEntity);
        ClassroomEntity finalClassroomEntity = classroomEntity;
        users.forEach(user -> {
          List<ClassroomEntity> prevClasses = user.getClassroom();
          prevClasses.add(finalClassroomEntity);
          user.setClassroom(prevClasses);
          userRepository.save(user);
        });
      } else {
        prevUsers = classroomEntity.getTeachers();
        prevUsers.addAll(users);
        classroomEntity.setTeachers(prevUsers);
        classroomRepository.save(classroomEntity);
        ClassroomEntity finalClassroomEntity = classroomEntity;
        users.forEach(user -> {
          List<ClassroomEntity> prevClasses = user.getClassroom();
          prevClasses.add(finalClassroomEntity);
          user.setClassroom(prevClasses);
          userRepository.save(user);
        });
      }
      return ResponseEntity.status(HttpStatus.OK)
          .body(Map.of("message", addDeleteUserModel.getType() + " have been added successfully"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error adding users"));
    }
  }

  public ResponseEntity<Object> joinByCode(JoinByCodeModel joinByCodeModel) {
    try {
      Optional<UserEntity> userEntityOptional = userRepository.findByEmail(joinByCodeModel.email);
      UserEntity userEntity = new UserEntity();
      if (userEntityOptional.isPresent()) {
        userEntity = userEntityOptional.get();
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
      }
      Optional<ClassroomEntity> classroomEntityOptional = classroomRepository
          .findClassroomEntityByCode(joinByCodeModel.code);
      ClassroomEntity classroomEntity = new ClassroomEntity();
      if (classroomEntityOptional.isPresent()) {
        classroomEntity = classroomEntityOptional.get();
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
      }
      if (userEntity.getClassroom().contains(classroomEntity)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Already exists in this classroom"));
      }
      List<ClassroomEntity> classroomEntities = userEntity.getClassroom();
      classroomEntities.add(classroomEntity);
      userEntity.setClassroom(classroomEntities);
      userRepository.save(userEntity);
      List<UserEntity> students = classroomEntity.getStudents();
      students.add(userEntity);
      classroomEntity.setStudents(students);
      classroomRepository.save(classroomEntity);
      return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Successfully joined the classroom"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Map.of("message", "Couldn't join the classroom"));
    }
  }

  public ResponseEntity<Object> getTeachers(String classroomId) {
    Optional<ClassroomEntity> classroomEntityOptional = classroomRepository.findById(classroomId);
    if (classroomEntityOptional.isPresent()) {
      ClassroomEntity classroomEntity = classroomEntityOptional.get();
      return ResponseEntity.status(HttpStatus.OK).body(classroomEntity.getTeachers());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
    }
  }

  public ResponseEntity<Object> getStudents(String classroomId) {
    Optional<ClassroomEntity> classroomEntityOptional = classroomRepository.findById(classroomId);
    if (classroomEntityOptional.isPresent()) {
      ClassroomEntity classroomEntity = classroomEntityOptional.get();
      return ResponseEntity.status(HttpStatus.OK).body(classroomEntity.getStudents());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
    }
  }

  public ResponseEntity<Object> leaveClassroom(LeaveClassroomModel leaveClassroomModel) {
    try {
      Optional<ClassroomEntity> classroomEntityOptional = classroomRepository
          .findById(leaveClassroomModel.getClassroomId());
      if (classroomEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
      }
      ClassroomEntity classroomEntity = classroomEntityOptional.get();
      Optional<UserEntity> userEntityOptional = userRepository.findByEmail(leaveClassroomModel.getEmail());
      if (userEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
      }
      UserEntity userEntity = userEntityOptional.get();
      if (classroomEntity.getCreator() == userEntity) {
        return deleteClassroom(classroomEntity);
      }
      List<ClassroomEntity> tempClasses = userEntity.getClassroom();
      tempClasses.remove(classroomEntity);
      userEntity.setClassroom(tempClasses);
      userRepository.save(userEntity);
      List<UserEntity> tempUsers;
      if (classroomEntity.getStudents().contains(userEntity)) {
        tempUsers = classroomEntity.getStudents();
        tempUsers.remove(userEntity);
        classroomEntity.setStudents(tempUsers);
      } else {
        tempUsers = classroomEntity.getTeachers();
        tempUsers.remove(userEntity);
        classroomEntity.setTeachers(tempUsers);
      }
      classroomRepository.save(classroomEntity);
      return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Left the classroom successfully"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Map.of("message", "Couldn't leave the classroom"));
    }
  }

  public ResponseEntity<Object> removeClassroom(LeaveClassroomModel leaveClassroomModel) {
    Optional<ClassroomEntity> classroomEntityOptional = classroomRepository
        .findById(leaveClassroomModel.getClassroomId());
    if (classroomEntityOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
    }
    ClassroomEntity classroomEntity = classroomEntityOptional.get();
    Optional<UserEntity> userEntityOptional = userRepository.findByEmail(leaveClassroomModel.getEmail());
    if (userEntityOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
    }
    UserEntity userEntity = userEntityOptional.get();
    if (classroomEntity.getCreator() == userEntity) {
      return deleteClassroom(classroomEntity);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Map.of("message", "Only creator can remove a classroom"));
    }
  }

  private ResponseEntity<Object> deleteClassroom(ClassroomEntity classroomEntity) {
    List<UserEntity> tempUsers = userRepository.findUserEntitiesByClassroomContains(classroomEntity);
    tempUsers.forEach(tempUser -> {
      tempUser.getClassroom().remove(classroomEntity);
      userRepository.save(tempUser);
    });
    classroomRepository.delete(classroomEntity);
    return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Classroom has been deleted successfully"));
  }
}
