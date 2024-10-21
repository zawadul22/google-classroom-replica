package com.glcl.backend.repository;

import com.glcl.backend.Entity.PostEntity;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@NonNullApi
@Repository
public interface PostRepository extends MongoRepository<PostEntity, String> {
}
