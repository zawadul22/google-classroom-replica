package com.glcl.backend.repository;

import com.glcl.backend.Entity.ClassroomEntity;
import com.glcl.backend.Entity.UserEntity;
import com.mongodb.lang.NonNullApi;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends MongoRepository<ClassroomEntity, String> {
  ClassroomEntity findByClassroomName(String classroomName);
  Optional<ClassroomEntity> findById(String id);
  boolean existsClassroomEntityByStudentsOrTeachers(List<UserEntity> students, List<UserEntity> teachers);
}
