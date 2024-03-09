package com.example.scientificproductionssystem.controllers;

import com.example.scientificproductionssystem.model.Institute;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.services.InstituteService;
import com.example.scientificproductionssystem.services.ResearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController /*ela define métodos que tratam requisições HTTP e retornam dados no formato JSON, XML ou outro formato de dados,*/
@RequestMapping("/researcher") /* todos os endpoints definidos nesta classe serão acessíveis através de URLs que começam com /researcher*/
public class researcherController {

    @Autowired /*Fornece uma instância automaticamente*/
    ResearcherService service; /*Cria uma instancia de ResearcherService e atribui ao service*/

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)/*método irá responder a solicitações GET e produzirá uma resposta no formato JSON*/
    public ResponseEntity<List<Researcher>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Researcher findById(@PathVariable(value = "id") Long id){
        return service.findById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Researcher create(@RequestBody Researcher researcher){
        return service.create(researcher);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Researcher update (@RequestBody Researcher researcher) {
        return service.update(researcher);
    }
}
