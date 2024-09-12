package com.glcl.backend.service;

import com.glcl.backend.Entity.UserEntity;
import com.glcl.backend.model.UserModel;
import com.glcl.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public ResponseEntity<Object> create(@RequestBody UserModel userModel) {
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

  public ResponseEntity<Object> getData() {
    return ResponseEntity.ok().body(userRepository.findAll());
  }

  public ResponseEntity<Object> findByName(String name) {
    return ResponseEntity.ok().body(userRepository.findByName(name));
  }
}
