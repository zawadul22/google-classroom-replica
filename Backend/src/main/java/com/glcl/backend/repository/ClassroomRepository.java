package com.glcl.backend.repository;

import com.glcl.backend.Entity.ClassroomEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassroomRepository extends MongoRepository<ClassroomEntity, String> {

}