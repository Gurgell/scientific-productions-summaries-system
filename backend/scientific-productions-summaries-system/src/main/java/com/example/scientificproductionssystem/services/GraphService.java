package com.example.scientificproductionssystem.services;

import com.example.scientificproductionssystem.dto.GraphDTO;
import com.example.scientificproductionssystem.model.QuoteName;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.model.Work;
import com.example.scientificproductionssystem.repositories.ResearcherRepository;

import org.jgrapht.Graph;

import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Service
public class GraphService {

    private static final Logger logger = LoggerFactory.getLogger(GraphService.class);

    @Autowired
    private ResearcherRepository researcherRepository;

    //private static final double SIMILARITY_THRESHOLD = 0.25;

    public Graph<Researcher, DefaultWeightedEdge> buildGraph() {
        List<Researcher> researchers = researcherRepository.findAll();
        Graph<Researcher, DefaultWeightedEdge> graph = new DefaultUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

        for (Researcher researcher : researchers) {
            graph.addVertex(researcher);
        }


        for (Researcher researcher : researchers) {
            if (researcher != null && researcher.getWorks() != null) {
                for (Work work : researcher.getWorks()) {
                    for (QuoteName quoteName : work.getQuoteNames()) {
                        Researcher connectedResearcher = findResearchersByQuoteName(quoteName.getName(), researchers.stream()
                                .filter(r -> r != null && !r.equals(researcher))
                                .collect(Collectors.toList()), work, researcher);
                        if (graph.containsVertex(connectedResearcher)) {
                            DefaultWeightedEdge edge = graph.getEdge(researcher, connectedResearcher);
                            if (edge == null) {
                                edge = graph.addEdge(researcher, connectedResearcher);
                                graph.setEdgeWeight(edge, 1);
                            } else {
                                logger.info(researcher.getName() + " - " + connectedResearcher.getName() + " Trabalho: " + work.getId());
                                double currentWeight = graph.getEdgeWeight(edge);
                                graph.setEdgeWeight(edge, currentWeight + 1);
                            }
                        }
                    }
                }
            }
        }

        return graph;
    }


    private Researcher findResearchersByQuoteName(String quoteName, List<Researcher> researchers, Work work, Researcher comparedResearcher) {
        Researcher matchedResearcher = new Researcher();

        for (Researcher researcher : researchers) {
            String researcherAvailableQuoteNames = researcher.getAvailableQuoteNames();
            if (researcherAvailableQuoteNames != null) {
                if (researcherAvailableQuoteNames.contains(quoteName)) {
                    logger.info("O nome de citação: " + quoteName + " retornou o seguinte pesquisador: " + researcher.getName() + " ("+ researcher.getId() +") " + "work: " + work.getId() + " pesquisador comparado: " + comparedResearcher.getName()+ " ("+ comparedResearcher.getId() +") ");
                    return researcher;
                }
            }

//            if (researcherAvailableQuoteNames != null){
//                double similarity = calculateJaccardSimilarity(quoteName, researcherAvailableQuoteNames);
////            if (similarity != 0.0){
////                logger.info("Comparing {} with {}, similarity: {}", quoteName, researcherAvailableQuoteNames, similarity);
////            }
//                if (similarity >= SIMILARITY_THRESHOLD) {
//                    if (similarity > lastResearcherSimilarity){
//                        lastResearcherSimilarity = similarity;
//                        matchedResearcher = researcher;
//                    }
//
//                }
//            }
        }
        return matchedResearcher;
    }

    public GraphDTO convertGraphToJson(Graph<Researcher, DefaultWeightedEdge> graph) {
        List<GraphDTO.Vertex> nodes = graph.vertexSet().stream()
                .map(r -> new GraphDTO.Vertex(r.getId(), r.getName()))
                .collect(Collectors.toList());

        List<GraphDTO.Edge> edges = graph.edgeSet().stream()
                .map(e -> new GraphDTO.Edge(graph.getEdgeSource(e).getId(), graph.getEdgeTarget(e).getId(), graph.getEdgeWeight(e)))
                .collect(Collectors.toList());

        GraphDTO graphDTO = new GraphDTO(nodes, edges);

        return graphDTO;
    }

    public double calculateJaccardSimilarity(String str1, String str2) {
        Set<String> set1 = new HashSet<>(Arrays.asList(str1.toLowerCase().split("\\s+|,\\s*|\\.\\s*")));
        Set<String> set2 = new HashSet<>(Arrays.asList(str2.toLowerCase().split("\\s+|,\\s*|\\.\\s*")));

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        return (double) intersection.size() / union.size();
    }
}
