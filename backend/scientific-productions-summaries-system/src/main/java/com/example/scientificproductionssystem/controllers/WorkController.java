package com.example.scientificproductionssystem.controllers;

import com.example.scientificproductionssystem.dto.researcher.ResearcherDetailsDTO;
import com.example.scientificproductionssystem.dto.work.WorkDetailsDTO;
import com.example.scientificproductionssystem.services.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/work")

public class WorkController {
    @Autowired
    WorkService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)/*método irá responder a solicitações GET e produzirá uma resposta no formato JSON*/
    public ResponseEntity<List<WorkDetailsDTO>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public WorkDetailsDTO findById(@PathVariable(value = "id") Long id){
        return service.findById(id);
    }

}
