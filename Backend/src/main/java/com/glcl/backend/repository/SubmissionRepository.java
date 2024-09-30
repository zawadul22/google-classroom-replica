package com.glcl.backend.repository;

import com.glcl.backend.Entity.SubmissionEntity;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@NonNullApi
@Repository
public interface SubmissionRepository extends MongoRepository<SubmissionEntity, String> {
}