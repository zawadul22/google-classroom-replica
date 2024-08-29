package com.glcl.backend.controller;

import com.glcl.backend.service.TableCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/table")
public class TableController {

    private final TableCreate tableCreate;

    @PostMapping(value = "/create")
    public ResponseEntity<Object> TableCreatePath(@RequestParam("name") String tableName){
        return tableCreate.createTable(tableName);
    }

}
