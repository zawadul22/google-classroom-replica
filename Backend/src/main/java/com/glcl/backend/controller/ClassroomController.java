package com.glcl.backend.controller;

import com.glcl.backend.model.AddUserModel;
import com.glcl.backend.model.ClassroomCreateModel;
import com.glcl.backend.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(value = "/classroom")
public class ClassroomController {
  private final ClassroomService classroomService;

  @PostMapping(value = "/create")
  public ResponseEntity<Object> createClassController(@RequestBody ClassroomCreateModel classroomCreateModel){
    return classroomService.createClass(classroomCreateModel);
  }

  @PutMapping(value = "/addUser")
  public ResponseEntity<Object> addUserToClassroomController(@RequestBody AddUserModel addUserModel){
    return classroomService.addUser(addUserModel);
  }
}
