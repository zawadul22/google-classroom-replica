package com.glcl.backend.controller;

import com.glcl.backend.model.AssignmentSubmitModel;
import com.glcl.backend.model.DeleteSubmissionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.glcl.backend.model.AssignmentCreateModel;
import com.glcl.backend.service.AssignmentService;

import lombok.RequiredArgsConstructor;

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

    @PostMapping("/submit")
    public ResponseEntity<Object> assignmentSubmitController(@RequestPart("body") AssignmentSubmitModel assignmentSubmitModel, @RequestPart("file") MultipartFile multipartFile) {
        return assignmentService.assignmentSubmit(assignmentSubmitModel, multipartFile);
    }

    @DeleteMapping("/delete/submission")
    public ResponseEntity<Object> submissionDeleteController(@RequestBody DeleteSubmissionModel deleteSubmissionModel) {
        return assignmentService.deleteSubmission(deleteSubmissionModel);
    }

    @DeleteMapping("/delete/assignment")
    public ResponseEntity<Object> assignmentDeleteController(@RequestBody AssignmentSubmitModel assignmentSubmitModel) {
        return assignmentService.deleteAssignment(assignmentSubmitModel);
    }
}
