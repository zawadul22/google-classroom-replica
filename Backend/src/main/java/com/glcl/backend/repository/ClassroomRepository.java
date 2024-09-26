package com.glcl.backend.repository;

import com.glcl.backend.Entity.ClassroomEntity;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@NonNullApi
@Repository
public interface ClassroomRepository extends MongoRepository<ClassroomEntity, String> {
  ClassroomEntity findByClassroomName(String classroomName);
  Optional<ClassroomEntity> findById(String id);
  Optional<ClassroomEntity> findClassroomEntityByCode(String code);
}
