package com.glcl.backend.service;

import com.glcl.backend.Entity.UserEntity;
import com.glcl.backend.model.UserModel;
import com.glcl.backend.repository.ClassroomRepository;
import com.glcl.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final ClassroomRepository classroomRepository;

  public ResponseEntity<Object> create(UserModel userModel) {
    try {
      UserEntity userEntity = UserEntity.builder()
              .name(userModel.getName())
              .email(userModel.getEmail())
              .password(userModel.getPassword())
              .build();
      userRepository.save(userEntity);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      e.getMessage();
      return ResponseEntity.internalServerError().build();
    }
  }

  private ResponseEntity<Object> getUserClass(String email) {
    try{
      List<UserEntity> userEntity = userRepository.findClassroomsByEmail(email);
      return ResponseEntity.ok().body(userEntity);
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  public ResponseEntity<Object> getData() {
    return ResponseEntity.ok().body(userRepository.findAll());
  }

  public ResponseEntity<Object> findByName(String name) {
    return ResponseEntity.ok().body(userRepository.findByName(name));
  }
}
