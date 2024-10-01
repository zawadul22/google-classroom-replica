package com.glcl.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.glcl.backend.model.AssignmentCreateModel;
import com.glcl.backend.service.AssignmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping("/create")
    public ResponseEntity<Object> assignmentCreateController(@RequestPart("body") AssignmentCreateModel assignmentCreateModel,
            @RequestPart("file") MultipartFile multipartFile) {
        return assignmentService.assignmentCreate(assignmentCreateModel, multipartFile);
    }

}
