package com.glcl.backend.controller;

import com.glcl.backend.Entity.Table1Entity;
import com.glcl.backend.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/table")
@CrossOrigin
public class TableController {

    private final TableService tableService;

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody Table1Entity table1Entity) {
        return tableService.create(table1Entity);
    }

    @GetMapping
    public ResponseEntity<Object> getTables() {
        return tableService.getData();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Object> getName(@PathVariable String name){
        return tableService.findByName(name);
    }
}
