package com.glcl.backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "assignment")
public class AssignmentEntity {
  @Id
  private String id;
  private String title;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime deadline;
  @JsonBackReference
  @Reference(UserEntity.class)
  private UserEntity createdBy;
  @JsonBackReference
  @Reference(ClassroomEntity.class)
  private ClassroomEntity classroom;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AssignmentEntity that = (AssignmentEntity) o;
    return id.equals(that.id); // Compare only by ID
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
