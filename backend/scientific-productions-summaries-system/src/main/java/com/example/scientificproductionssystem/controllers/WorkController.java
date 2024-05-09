package com.example.scientificproductionssystem.controllers;

import com.example.scientificproductionssystem.dto.researcher.ResearcherDetailsDTO;
import com.example.scientificproductionssystem.dto.work.WorkDetailsDTO;
import com.example.scientificproductionssystem.services.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping(value="/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<WorkDetailsDTO>> findWithParams(@RequestParam(name = "page", required = false,
            defaultValue = "0") Integer page,
                                                                     @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit,
                                                                     @RequestParam(name = "startYear", required = false, defaultValue = "1900") Integer startYear,
                                                                     @RequestParam(name = "endYear", required = false, defaultValue = "2030") Integer endYear,
                                                                     @RequestParam(name = "idInstitute", required = false) Optional<Long> idInstitute,
                                                                     @RequestParam(name = "idResearcher", required = false) Optional<Long> idResearcher,
                                                                     @RequestParam(name = "type", required = false, defaultValue = "all") String type)
    {
        return ResponseEntity.ok(service.findWithParams(page, limit, startYear, endYear, idInstitute, idResearcher, type));
    }

//    @GetMapping(value="/searchv2", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Page<WorkDetailsDTO>> findAllByDateOrInstituteOrResearcherOrType(@RequestParam(name = "page", required = false,
//            defaultValue = "0") Integer page,
//                                                               @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit,
//                                                               @RequestParam(name = "startYear", required = false, defaultValue = "0000") Integer startYear,
//                                                               @RequestParam(name = "endYear", required = false, defaultValue = "2030") Integer endYear,
//                                                               @RequestParam(name = "idInstitute", required = false) Long idInstitute,
//                                                               @RequestParam(name = "idResearcher", required = false) Long idResearcher,
//                                                               @RequestParam(name = "type", required = false, defaultValue = "todos") String type)
//    {
//        return ResponseEntity.ok(service.findAllByDateOrInstituteOrResearcherOrType(page, limit, startYear, endYear, idInstitute, idResearcher, type));
//    }
}
