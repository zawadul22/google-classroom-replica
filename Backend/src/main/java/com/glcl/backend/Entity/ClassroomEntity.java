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

import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "classrooms")
public class ClassroomEntity {
  @Id
  private String id;
  private String classroomName;
  private String code;
  @JsonBackReference
  @Reference(UserEntity.class)
  private List<UserEntity> teachers;
  @JsonBackReference
  @Reference(UserEntity.class)
  private List<UserEntity> students;
  @JsonBackReference
  @Reference(UserEntity.class)
  private UserEntity creator;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ClassroomEntity that = (ClassroomEntity) o;
    return id.equals(that.id); // Compare only by ID
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
