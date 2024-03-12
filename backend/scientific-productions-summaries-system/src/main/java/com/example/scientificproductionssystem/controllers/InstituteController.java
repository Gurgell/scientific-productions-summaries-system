package com.example.scientificproductionssystem.controllers;

import com.example.scientificproductionssystem.dto.institute.InstituteDetailsDTO;
import com.example.scientificproductionssystem.dto.institute.InstituteUpdateDTO;
import com.example.scientificproductionssystem.services.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping(value="/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InstituteDetailsDTO>> findWithParams(@RequestParam(name = "page", required = false,
            defaultValue = "0") Integer page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "name", required = false) Optional<String> name,
            @RequestParam(name = "acronym", required = false) Optional<String> acronym)
    {
            if(name.isPresent())
                return ResponseEntity.ok(service.findWithParams(page, limit, "name", name.get()));
            else if(acronym.isPresent())
                return ResponseEntity.ok(service.findWithParams(page, limit, "acronym", acronym.get()));
            else
                return ResponseEntity.ok(service.findWithParams(page, limit));
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
