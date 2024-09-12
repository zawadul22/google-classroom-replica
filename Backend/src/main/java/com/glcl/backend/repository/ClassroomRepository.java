package com.glcl.backend.repository;

import com.glcl.backend.Entity.ClassroomEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends MongoRepository<ClassroomEntity, String> {

}
