package com.glcl.backend.model.postModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPostModel {
  private String post;
  private String creator;
  private byte[] file;
  private String fileName;
}
