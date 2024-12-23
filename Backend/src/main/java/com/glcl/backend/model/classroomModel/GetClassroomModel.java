package com.glcl.backend.model.classroomModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetClassroomModel {
  private String id;
  private String classroomName;
}
