package com.example.scientificproductionssystem.controllers;

import com.example.scientificproductionssystem.dto.institute.InstituteDetailsDTO;
import com.example.scientificproductionssystem.dto.institute.InstituteUpdateDTO;
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
    public ResponseEntity<List<InstituteDetailsDTO>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public InstituteDetailsDTO findById(@PathVariable(value = "id") Long id){
        return service.findById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public InstituteDetailsDTO create(@RequestBody InstituteUpdateDTO instituteUpdateDTO){
        return service.create(instituteUpdateDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public InstituteDetailsDTO update (@RequestBody InstituteUpdateDTO instituteUpdateDTO, @PathVariable Long id) {
        return service.update(instituteUpdateDTO, id);
    }
}
