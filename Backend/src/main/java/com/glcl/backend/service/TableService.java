package com.glcl.backend.service;

import com.glcl.backend.Entity.Table1Entity;
import com.glcl.backend.repository.Table1Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class TableService {

    private final Table1Repository table1Repository;

    public ResponseEntity<Object> create(@RequestBody Table1Entity table1){
        try{
            table1Repository.save(table1);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.getMessage();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> getData(){
        return ResponseEntity.ok().body(table1Repository.findAll());
    }
}
