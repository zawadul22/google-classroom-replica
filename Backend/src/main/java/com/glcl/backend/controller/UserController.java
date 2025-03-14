package com.glcl.backend.controller;

import com.glcl.backend.model.userModel.UserModel;
import com.glcl.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @PostMapping("/save")
  public ResponseEntity<Object> save(@RequestBody UserModel userModel) {
    return userService.create(userModel);
  }

  @GetMapping("/classroom/{email}")
  public ResponseEntity<Object> getClassroom(@PathVariable String email) {
    return userService.getUserClassroom(email);
  }
}