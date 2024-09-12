package com.glcl.backend.controller;

import com.glcl.backend.Entity.UserEntity;
import com.glcl.backend.model.UserModel;
import com.glcl.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/table")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody UserModel userModel) {
        return userService.create(userModel);
    }

    @GetMapping
    public ResponseEntity<Object> getTables() {
        return userService.getData();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Object> getName(@PathVariable String name){
        return userService.findByName(name);
    }
}
