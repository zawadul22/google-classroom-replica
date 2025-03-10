package com.glcl.backend.service;

import com.glcl.backend.Entity.UserEntity;
import com.glcl.backend.model.classroomModel.GetClassroomModel;
import com.glcl.backend.model.classroomModel.GetUserClassroomList;
import com.glcl.backend.model.userModel.UserModel;
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

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final ClassroomRepository classroomRepository;

  public ResponseEntity<Object> create(UserModel userModel) {
    try {
      UserEntity userEntity = UserEntity.builder().name(userModel.getName()).email(userModel.getEmail()).password(userModel.getPassword()).build();
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
      List<GetClassroomModel> asTeacher = new ArrayList<>();
      List<GetClassroomModel> asStudent = userEntity.getClassroom().stream().filter(c -> {
        if (c.getTeachers().contains(userEntity)) {
          Integer count = c.getStudents().size() + c.getTeachers().size();
          asTeacher.add(new GetClassroomModel(c.getId(), c.getClassroomName(), c.getCreator().getName(), c.getSection(), count));
          return false;
        } else {
          return true;
        }
      }).map(c -> {
        Integer count = c.getStudents().size() + c.getTeachers().size();
        return new GetClassroomModel(c.getId(), c.getClassroomName(), c.getCreator().getName(), c.getSection(), count);
      }).toList();
      GetUserClassroomList getUserClassroomList = GetUserClassroomList.builder().asTeacher(asTeacher).asStudent(asStudent).build();
      return ResponseEntity.ok().body(getUserClassroomList);
//      return null;
    } catch (Exception e) {
      throw new RuntimeException(e.getCause());
    }
  }
}
