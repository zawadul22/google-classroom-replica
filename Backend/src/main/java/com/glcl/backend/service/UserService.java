package com.glcl.backend.service;

import com.glcl.backend.Entity.UserEntity;
import com.glcl.backend.model.classroomModel.GetClassroomModel;
import com.glcl.backend.model.userModel.UserModel;
import com.glcl.backend.repository.ClassroomRepository;
import com.glcl.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
      return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User created"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError().build();
    }
  }

  public ResponseEntity<Object> getUserClassroom(String email) {
    try {
      Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
      assert userEntityOptional.isPresent();
      UserEntity userEntity = userEntityOptional.get();
      List<GetClassroomModel> classes2 = userEntity.getClassroom()
              .stream()
              .map(c -> new GetClassroomModel(
                      c.getId(),
                      c.getClassroomName()
              ))
              .toList();
      return ResponseEntity.ok().body(classes2);
    } catch (Exception e) {
      throw new RuntimeException(e.getCause());
    }
  }
}
