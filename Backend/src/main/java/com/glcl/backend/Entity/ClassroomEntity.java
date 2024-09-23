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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "classrooms")
public class ClassroomEntity {
  @Id
  private String id;
  private String classroomName;
  @JsonBackReference
  @Reference(UserEntity.class)
  private List<UserEntity> teachers;
  @JsonBackReference
  @Reference(UserEntity.class)
  private List<UserEntity> students;
}
