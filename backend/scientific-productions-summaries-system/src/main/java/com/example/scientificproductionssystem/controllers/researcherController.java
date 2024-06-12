package com.example.scientificproductionssystem.controllers;

import com.example.scientificproductionssystem.dto.institute.InstituteDetailsDTO;
import com.example.scientificproductionssystem.dto.researcher.ResearcherDetailsDTO;
import com.example.scientificproductionssystem.dto.researcher.ResearcherUpdateDTO;
import com.example.scientificproductionssystem.services.ResearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController /*ela define métodos que tratam requisições HTTP e retornam dados no formato JSON, XML ou outro formato de dados,*/
@CrossOrigin
@RequestMapping("/researcher") /* todos os endpoints definidos nesta classe serão acessíveis através de URLs que começam com /researcher*/
public class researcherController {

    @Autowired /*Fornece uma instância automaticamente*/
    ResearcherService service; /*Cria uma instancia de ResearcherService e atribui ao service*/

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)/*método irá responder a solicitações GET e produzirá uma resposta no formato JSON*/
    public ResponseEntity<List<ResearcherDetailsDTO>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResearcherDetailsDTO findById(@PathVariable(value = "id") Long id){
        return service.findById(id);
    }

    @GetMapping(value="/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ResearcherDetailsDTO>> findWithParams(@RequestParam(name = "page", required = false,
            defaultValue = "0") Integer page,
                                                                    @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit,
                                                                     @RequestParam(name = "field", required = false) Optional<String> field,
                                                                     @RequestParam(name = "term", required = false) Optional<String> term)
    {
        return ResponseEntity.ok(service.findWithParams(page, limit, field, term));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResearcherDetailsDTO create(@RequestBody ResearcherDetailsDTO researcher){
        return service.create(researcher);
    }

    @GetMapping(value = "/curriculum/{curriculumId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResearcherUpdateDTO findResearcherByCurriculumId(@PathVariable(value = "curriculumId") Long curriculumId){
        return service.findResearcherByCurriculumId(curriculumId);
    }

    @PostMapping(value = "/findAllByListOfId", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<ResearcherDetailsDTO> findAllByListOfId(@RequestBody List<Long> ids){
        return service.findAllById(ids);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
