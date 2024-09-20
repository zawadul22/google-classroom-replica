package com.glcl.backend.Entity;

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
  @Reference(UserEntity.class)
  private List<UserEntity> teachers;
  @Reference(UserEntity.class)
  private List<UserEntity> students;
}
