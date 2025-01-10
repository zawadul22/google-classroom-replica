package com.glcl.backend.model.assignmentModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAssignmentModel {
  private String id;
  private String title;
  private String description;
  private String createdAt;
  private String deadline;
  private String createdByName;
  private String createdByEmail;
  private byte[] file;
}
