package com.glcl.backend.repository;

import com.glcl.backend.Entity.Table1Entity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Table1Repository extends MongoRepository<Table1Entity, String> {
    Optional<Table1Entity> findByName(String tableName);
}
