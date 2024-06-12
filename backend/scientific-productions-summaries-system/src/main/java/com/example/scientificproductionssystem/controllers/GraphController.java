package com.example.scientificproductionssystem.controllers;

import com.example.scientificproductionssystem.dto.GraphDTO;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.services.GraphService;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<?> getResearcherGraph() {
        Graph<Researcher, DefaultWeightedEdge> graph = graphService.buildGraph();
        GraphDTO graphDTO = graphService.convertGraphToJson(graph);
        return ResponseEntity.ok(graphDTO);
    }
}

