package com.glcl.backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
//public class UserEntity implements UserDetails {
public class UserEntity {
  @Id
  private String id;
  private String name;
  private String email;
  private String password;
  @JsonBackReference
  @Reference(ClassroomEntity.class)
  private List<ClassroomEntity> classroom;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserEntity that = (UserEntity) o;
    return id.equals(that.id); // Compare only by ID
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

//  @Override
//  public Collection<? extends GrantedAuthority> getAuthorities() {
//    return List.of();
//  }
//
//  @Override
//  public String getUsername() {
//    return email;
//  }
//
//  @Override
//  public String getPassword() {
//    return password;
//  }
//
//  @Override
//  public boolean isAccountNonExpired() {
//    return true;
//  }
//
//  @Override
//  public boolean isAccountNonLocked() {
//    return true;
//  }
//
//  @Override
//  public boolean isCredentialsNonExpired() {
//    return true;
//  }
//
//  @Override
//  public boolean isEnabled() {
//    return true;
//  }
}
