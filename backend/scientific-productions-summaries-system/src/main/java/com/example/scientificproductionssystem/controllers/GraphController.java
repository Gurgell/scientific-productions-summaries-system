package com.example.scientificproductionssystem.controllers;

import com.example.scientificproductionssystem.dto.GraphDTO;
import com.example.scientificproductionssystem.dto.researcher.ResearcherDetailsDTO;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.services.GraphService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graph")
@CrossOrigin
public class GraphController {
    @Autowired
    private GraphService graphService;

    @PostMapping
    public ResponseEntity<?> createResearchersGraph(@RequestBody @Nullable List<Long> ids,
                                                    @RequestParam(name = "productionType", required = false, defaultValue = "all") String productionType,
                                                    @RequestParam(name = "vertexType", required = false, defaultValue = "researcher") String vertexType)
    {
        GraphDTO graphDTO = graphService.buildGraph(ids, productionType, vertexType);

        return ResponseEntity.ok(graphDTO);
    }
}

