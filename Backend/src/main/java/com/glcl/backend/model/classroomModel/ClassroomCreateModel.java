package com.glcl.backend.model.classroomModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomCreateModel {
  private String classroomName;
  private String teacherName;
  private String section;
}