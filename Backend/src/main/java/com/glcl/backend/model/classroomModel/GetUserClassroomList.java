package com.glcl.backend.model.classroomModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserClassroomList {
  private List<GetClassroomModel> asTeacher;
  private List<GetClassroomModel> asStudent;
}