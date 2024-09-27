package com.glcl.backend.controller;

import com.glcl.backend.model.AddDeleteUserModel;
import com.glcl.backend.model.ClassroomCreateModel;
import com.glcl.backend.model.JoinByCodeModel;
import com.glcl.backend.model.LeaveClassroomModel;
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
  public ResponseEntity<Object> createClassController(@RequestBody ClassroomCreateModel classroomCreateModel) {
    return classroomService.createClass(classroomCreateModel);
  }

  @PutMapping(value = "/addUser")
  public ResponseEntity<Object> addUserToClassroomController(@RequestBody AddDeleteUserModel addDeleteUserModel) {
    return classroomService.addUser(addDeleteUserModel);
  }

  @GetMapping(value = "/getTeachers")
  public ResponseEntity<Object> getTeachersController(@RequestParam String classroomId) {
    return classroomService.getTeachers(classroomId);
  }

  @GetMapping(value = "/getStudents")
  public ResponseEntity<Object> getStudentsController(@RequestParam String classroomId) {
    return classroomService.getStudents(classroomId);
  }

  @DeleteMapping(value = "/deleteUser")
  public ResponseEntity<Object> deleteUserController(@RequestBody AddDeleteUserModel addDeleteUserModel) {
    return null;
  }

  @PatchMapping(value = "/joinByCode")
  public ResponseEntity<Object> joinByCodeController(@RequestBody JoinByCodeModel joinByCodeModel) {
    return classroomService.joinByCode(joinByCodeModel);
  }

  @PatchMapping(value = "/leaveClassroom")
  public ResponseEntity<Object> leaveClassroomController(@RequestBody LeaveClassroomModel leaveClassroomModel) {
    return classroomService.leaveClassroom(leaveClassroomModel);
  }

  @DeleteMapping(value = "/removeClassroom")
  public ResponseEntity<Object> removeClassroomController(@RequestBody LeaveClassroomModel leaveClassroomModel){
    return classroomService.removeClassroom(leaveClassroomModel);
  }
}
