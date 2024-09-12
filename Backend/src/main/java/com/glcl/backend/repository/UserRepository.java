package com.glcl.backend.repository;

import com.glcl.backend.Entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByName(String tableName);
    Optional<UserEntity> findByEmail(String email);
}
