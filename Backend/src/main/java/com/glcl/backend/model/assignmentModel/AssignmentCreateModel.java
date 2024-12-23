package com.glcl.backend.model.assignmentModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentCreateModel {
  private String title;
  private String description;
  private String createdAt;
  private String deadline;
  private String createdBy;
  private String classroomId;
}
