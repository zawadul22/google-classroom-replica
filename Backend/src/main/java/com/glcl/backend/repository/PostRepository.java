package com.glcl.backend.repository;

import com.glcl.backend.Entity.ClassroomEntity;
import com.glcl.backend.Entity.PostEntity;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@NonNullApi
@Repository
public interface PostRepository extends MongoRepository<PostEntity, String> {
  public List<PostEntity> getPostEntitiesByClassroom(ClassroomEntity classroomEntity);
}
