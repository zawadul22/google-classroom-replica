package com.glcl.backend.model;

import com.glcl.backend.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
public class ClassroomModel {
  private String classroomName;
  private String teacherName;
}
