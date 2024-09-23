package com.glcl.backend.service;

import com.glcl.backend.Entity.ClassroomEntity;
import com.glcl.backend.Entity.UserEntity;
import com.glcl.backend.model.AddUserModel;
import com.glcl.backend.model.ClassroomCreateModel;
import com.glcl.backend.repository.ClassroomRepository;
import com.glcl.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

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

  public ResponseEntity<Object> addUser(AddUserModel addUserModel) {
    try {
      AtomicInteger flag = new AtomicInteger();
      List<UserEntity> userEntities = addUserModel.getEmail().stream()
              .map(user -> {
                Optional<UserEntity> userEntityOptional = userRepository.findByEmail(user);
                assert userEntityOptional.isPresent();
                return userEntityOptional.get();
              })
              .filter(user -> {
                Optional<UserEntity> userEntityOptional = userRepository.findByEmail(user.getEmail());
                if (userEntityOptional.isEmpty()) {
                  flag.set(1);
                }
                return userEntityOptional.isEmpty();
              })
              .toList();
      if(userEntities.isEmpty()){
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Map.of("message", addUserModel.getType()+" already exists in classroom"));
      }
      Optional<ClassroomEntity> classroomEntityOptional = classroomRepository.findById(addUserModel.getClassroomId());
      assert classroomEntityOptional.isPresent();
      ClassroomEntity classroomEntity = classroomEntityOptional.get();
      if(addUserModel.getType().equals("student")) {
        classroomEntity.setStudents(userEntities);
      }
      else{
        classroomEntity.setTeachers(userEntities);
      }
      classroomRepository.save(classroomEntity);
      if(flag.get() == 1){
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Some "+addUserModel.getType()+"s already exist. Added remaining "+addUserModel.getType()+ "s."));
      }
      return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", addUserModel.getType()+" added successfully."));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
