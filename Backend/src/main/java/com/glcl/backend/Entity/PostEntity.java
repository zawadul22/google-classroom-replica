package com.glcl.backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "posts")
public class PostEntity {
  @Id
  private String id;
  private String post;
//  private Binary file;
  private String file;
  private String filename;
  @JsonBackReference
  @Reference(UserEntity.class)
  private UserEntity creator;

  @JsonBackReference
  @Reference(ClassroomEntity.class)
  private ClassroomEntity classroom;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PostEntity that = (PostEntity) o;
    return id.equals(that.id); // Compare only by ID
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
