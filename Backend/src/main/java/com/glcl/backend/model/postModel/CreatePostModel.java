package com.glcl.backend.model.postModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostModel {
  private String email;
  private String post;
  private String classroomId;
//  private String filename;
}
