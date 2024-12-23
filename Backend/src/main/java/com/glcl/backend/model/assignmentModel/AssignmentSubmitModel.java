package com.glcl.backend.model.assignmentModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentSubmitModel {
    private String assignmentId;
    private String studentEmail;
    private String classroomId;
}
