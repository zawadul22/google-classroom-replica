package com.glcl.backend.model.classroomModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetClassroomModel {
  private String id;
  private String classroomName;
  private String creator;
  private String section;
  private Integer totalUsers;
}
