package com.glcl.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteSubmissionModel {
    private String submissionId;
//    private String assignmentId;
    private String userEmail;
}
