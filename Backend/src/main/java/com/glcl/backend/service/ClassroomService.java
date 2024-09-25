package com.glcl.backend.service;

import com.glcl.backend.Entity.ClassroomEntity;
import com.glcl.backend.Entity.UserEntity;
import com.glcl.backend.model.AddDeleteUserModel;
import com.glcl.backend.model.ClassroomCreateModel;
import com.glcl.backend.repository.ClassroomRepository;
import com.glcl.backend.repository.UserRepository;
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
              .build();
      classroomRepository.save(classroomEntity);
      ClassroomEntity tempClassroomEntity = classroomRepository.findByClassroomName(classroomModel.getClassroomName());
      List<ClassroomEntity> classroomEntities = userEntity.getClassroom();
      classroomEntities.add(tempClassroomEntity);
      userEntity.setClassroom(classroomEntities);
      userRepository.save(userEntity);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public ResponseEntity<Object> addUser(AddDeleteUserModel addDeleteUserModel) {
    try {
      int flag = 0;
      Optional<ClassroomEntity> classroomEntityOptional = classroomRepository.findById(addDeleteUserModel.getClassroomId());
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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Couldn't add because user/s already exist/s in classroom"));
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
      return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", addDeleteUserModel.getType()+" have been added successfully"));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public ResponseEntity<Object> getTeachers(String classroomId) {
    Optional<ClassroomEntity> classroomEntityOptional = classroomRepository.findById(classroomId);
    if(classroomEntityOptional.isPresent()){
      ClassroomEntity classroomEntity = classroomEntityOptional.get();
      return ResponseEntity.status(HttpStatus.OK).body(classroomEntity.getTeachers());
    }
    else{
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
    }
  }

  public ResponseEntity<Object> getStudents(String classroomId) {
    Optional<ClassroomEntity> classroomEntityOptional = classroomRepository.findById(classroomId);
    if(classroomEntityOptional.isPresent()){
      ClassroomEntity classroomEntity = classroomEntityOptional.get();
      return ResponseEntity.status(HttpStatus.OK).body(classroomEntity.getStudents());
    }
    else{
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
    }
  }
}
