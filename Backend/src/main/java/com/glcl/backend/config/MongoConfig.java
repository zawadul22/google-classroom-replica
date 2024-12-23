package com.glcl.backend.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MongoConfig {
  private final MongoClient mongoClient;

  @Bean
  public GridFSBucket gridFSBucket() {
    MongoDatabase database = mongoClient.getDatabase("testdb");
    return GridFSBuckets.create(database);
  }
}
