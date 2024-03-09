package com.example.scientificproductionssystem.controllers;

import com.example.scientificproductionssystem.model.Institute;
import com.example.scientificproductionssystem.services.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin    //Notação para que seja possível fazer requisições em um frontend situado na mesma máquina do backend
@RequestMapping("/institute")
public class InstituteController {

    @Autowired
    InstituteService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Institute>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Institute findById(@PathVariable(value = "id") Long id){
        return service.findById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Institute create(@RequestBody Institute institute){
        return service.create(institute);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Institute update (@RequestBody Institute institute) {
        return service.update(institute);
    }
}
