package com.glcl.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class AssignmentCreateModel {
  private String title;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime deadline;
  private String createdBy;
  private String classroomId;
}
