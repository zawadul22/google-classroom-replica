package com.glcl.backend.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TableCreate {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public ResponseEntity<Object> createTable(String tableName) {
        try{
            String sql = "CREATE TABLE IF NOT EXISTS" + tableName + "( id SERIAL PRIMARY KEY, name VARCHAR(255), age INT)";
            entityManager.createNativeQuery(sql).executeUpdate();
            return ResponseEntity.status(HttpStatus.CREATED).body("Table created");
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not create table");
        }
    }
}
