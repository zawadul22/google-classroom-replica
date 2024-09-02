package com.glcl.backend.repository;

import com.glcl.backend.Entity.Table1Entity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Table1Repository extends MongoRepository<Table1Entity, String> {

}
