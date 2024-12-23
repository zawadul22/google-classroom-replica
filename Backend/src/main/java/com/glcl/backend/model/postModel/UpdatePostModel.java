package com.glcl.backend.model.postModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostModel {
  private String email;
  private String postId;
  private String description;
//  private boolean delete;
}
