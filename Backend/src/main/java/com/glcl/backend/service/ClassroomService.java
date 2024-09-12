package com.glcl.backend.service;

import com.glcl.backend.Entity.ClassroomEntity;
import com.glcl.backend.Entity.UserEntity;
import com.glcl.backend.model.ClassroomModel;
import com.glcl.backend.repository.ClassroomRepository;
import com.glcl.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassroomService {
  private final ClassroomRepository classroomRepository;
  private final UserRepository userRepository;

  public ResponseEntity<Object> createClass(ClassroomModel classroomModel) {
    try{
      Optional<UserEntity> userEntityOptional = userRepository.findByEmail(classroomModel.getTeacherName());
      UserEntity userEntity = userEntityOptional.get();
      assert userEntity != null;
      List<UserEntity> teacher = new ArrayList<>();
      teacher.add(userEntity);
      ClassroomEntity classroomEntity = ClassroomEntity.builder()
              .classroomName(classroomModel.getClassroomName())
              .teachers(teacher)
              .build();
      classroomRepository.save(classroomEntity);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
