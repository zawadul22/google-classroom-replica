package com.glcl.backend.model.assignmentModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetSubmissionModel {
  private String id;
  private String assignmentId;
  private LocalDateTime submissionDate;
  private boolean lateSubmission;
  private byte[] file;
  private String userName;
  private String userEmail;
}
