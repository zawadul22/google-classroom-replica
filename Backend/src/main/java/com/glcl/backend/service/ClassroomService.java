package com.glcl.backend.service;

import com.glcl.backend.Entity.ClassroomEntity;
import com.glcl.backend.Entity.UserEntity;
import com.glcl.backend.model.AddStudentModel;
import com.glcl.backend.model.ClassroomCreateModel;
import com.glcl.backend.repository.ClassroomRepository;
import com.glcl.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

  public ResponseEntity<Object> addStudent(AddStudentModel addStudentModel) {
    Optional<UserEntity> userEntityOptional = userRepository.findByEmail(addStudentModel.getEmail());
    assert userEntityOptional.isPresent();
    UserEntity userEntity = userEntityOptional.get();
    return null;
  }
}
