package com.glcl.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentCreateModel {
  private String title;
  private String description;
  // private LocalDateTime createdAt;
  private String deadline;
  private String createdBy;
  private String classroomId;
  // private MultipartFile file;
}
