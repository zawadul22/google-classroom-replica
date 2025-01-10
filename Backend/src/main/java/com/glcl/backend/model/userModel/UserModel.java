package com.glcl.backend.model.userModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@RequiredArgsConstructor
public class UserModel {
  private String name;
  private String email;
  private String password;
}
