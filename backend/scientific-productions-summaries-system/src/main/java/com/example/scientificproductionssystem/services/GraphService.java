package com.example.scientificproductionssystem.services;

import com.example.scientificproductionssystem.dto.GraphDTO;
import com.example.scientificproductionssystem.model.Institute;
import com.example.scientificproductionssystem.model.QuoteName;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.model.Work;
import com.example.scientificproductionssystem.model.enums.DesiredWorkType;
import com.example.scientificproductionssystem.model.worktypes.Article;
import com.example.scientificproductionssystem.model.worktypes.Book;
import com.example.scientificproductionssystem.repositories.InstituteRepository;
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

    @Autowired
    private InstituteRepository instituteRepository;

    //private static final double SIMILARITY_THRESHOLD = 0.25;

    public GraphDTO buildGraph(List<Long> ids, String productionType, String vertexType){

        List<Researcher> researchers;
        if (ids != null){
            researchers = researcherRepository.findAllById(ids);
        }else {
            researchers = researcherRepository.findAll();
        }

        DesiredWorkType workType = null;

        switch (productionType.toUpperCase()){
            case "ARTICLE": workType = DesiredWorkType.ARTICLE; break;
            case "BOOK": workType = DesiredWorkType.BOOK; break;
            default: break;
        }

        if (vertexType.equals("institute")) return convertInstituteGraphToJson(buildInstitutesGraph(researchers, Optional.ofNullable(workType)));

        return convertResearcherGraphToJson(buildResearchersGraph(researchers, Optional.ofNullable(workType)));

    }

    public Graph<Institute, DefaultWeightedEdge> buildInstitutesGraph(List<Researcher> researchers, Optional<DesiredWorkType> workType) {
        Graph<Institute, DefaultWeightedEdge> graph = new DefaultUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

        List<Institute> institutes = instituteRepository.findAllByResearchers(researchers);

        for (Institute institute : institutes) {
            graph.addVertex(institute);
        }

        for (Institute institute : institutes) {
            for (Researcher researcher : institute.getResearchers()) {
                if (researcher != null && researcher.getWorks() != null) {
                    for (Work work : researcher.getWorks()) {

                        if(workType.isPresent()){
                            if(workType.get() == DesiredWorkType.ARTICLE && work.getClass() != Article.class) continue;
                            if(workType.get() == DesiredWorkType.BOOK && work.getClass() != Book.class) continue;
                        }

                        for (QuoteName quoteName : work.getQuoteNames()) {

                            List<Researcher> connectedResearchers = researchers.stream()
                                    .filter(r -> !r.getInstitute().equals(institute)) // Excluir o próprio instituto
                                    .filter(r -> r.getAvailableQuoteNames() != null && r.getAvailableQuoteNames().contains(quoteName.getName()))
                                    .toList();
                            connectedResearchers.forEach(cr -> logger.info("Instituto atual: " + institute.getName() + "Pesquisador atual: " + researcher.getName() + " | Nome do pesquisador: " + cr.getName() + " Instituto achado: " + cr.getInstitute().getName()));

                            for (Researcher connectedResearcher : connectedResearchers) {
                                Institute connectedInstitute = connectedResearcher.getInstitute();
                                if (graph.containsVertex(connectedInstitute)) {
                                    DefaultWeightedEdge edge = graph.getEdge(institute, connectedInstitute);
                                    if (edge == null) {
                                        edge = graph.addEdge(institute, connectedInstitute);
                                        graph.setEdgeWeight(edge, 1);
                                    } else {
                                        double currentWeight = graph.getEdgeWeight(edge);
                                        graph.setEdgeWeight(edge, currentWeight + 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return graph;
    }

    public Graph<Researcher, DefaultWeightedEdge> buildResearchersGraph(List<Researcher> researchers, Optional<DesiredWorkType> workType) {
        Graph<Researcher, DefaultWeightedEdge> graph = new DefaultUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

        for (Researcher researcher : researchers) {
            graph.addVertex(researcher);
        }

        int numResearchers = researchers.size();

        for (int i = 0; i < numResearchers; i++) {
            Researcher researcher = researchers.get(i);

            if (researcher != null && researcher.getWorks() != null) {
                for (Work work : researcher.getWorks()) {
                    if (workType.isPresent()) {
                        if (workType.get() == DesiredWorkType.ARTICLE && work.getClass() != Article.class) continue;
                        if (workType.get() == DesiredWorkType.BOOK && work.getClass() != Book.class) continue;
                    }

                    for (QuoteName quoteName : work.getQuoteNames()) {
                        List<Researcher> remainingResearchers = researchers.subList(i + 1, numResearchers);
                        Researcher connectedResearcher = findResearchersByQuoteName(
                                quoteName.getName(),
                                remainingResearchers.stream().filter(r -> r != null).collect(Collectors.toList()),
                                work,
                                researcher
                        );

                        if (graph.containsVertex(connectedResearcher)) {
                            DefaultWeightedEdge edge = graph.getEdge(researcher, connectedResearcher);
                            if (edge == null) {
                                edge = graph.addEdge(researcher, connectedResearcher);
                                graph.setEdgeWeight(edge, 1);
                            } else {
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
                if (researcherAvailableQuoteNames.toLowerCase().contains(quoteName.toLowerCase())) {
                    logger.info("O nome de citação: " + quoteName + " instituto: " + researcher.getInstitute().getName() + " retornou o seguinte pesquisador: " + researcher.getName() + " ("+ researcher.getId() +") " + " || work: " + work.getId() + " "+ work.getTitle() + work.getYear() +  "instituto: " + comparedResearcher.getInstitute().getName() + " pesquisador comparado: " + comparedResearcher.getName()+ " ("+ comparedResearcher.getId() +") ");
                    return researcher;
                }
            }
        }
        return matchedResearcher;
    }

    public GraphDTO convertResearcherGraphToJson(Graph<Researcher, DefaultWeightedEdge> graph) {
        List<GraphDTO.Vertex> nodes = graph.vertexSet().stream()
                .map(r -> new GraphDTO.Vertex(r.getId(), r.getName()))
                .collect(Collectors.toList());

        List<GraphDTO.Edge> edges = graph.edgeSet().stream()
                .map(e -> new GraphDTO.Edge(graph.getEdgeSource(e).getId(), graph.getEdgeTarget(e).getId(), graph.getEdgeWeight(e)))
                .collect(Collectors.toList());

        GraphDTO graphDTO = new GraphDTO(nodes, edges);

        return graphDTO;
    }

    public GraphDTO convertInstituteGraphToJson(Graph<Institute, DefaultWeightedEdge> graph) {
        List<GraphDTO.Vertex> nodes = graph.vertexSet().stream()
                .map(r -> new GraphDTO.Vertex(r.getId(), r.getName()))
                .collect(Collectors.toList());

        List<GraphDTO.Edge> edges = graph.edgeSet().stream()
                .map(e -> new GraphDTO.Edge(graph.getEdgeSource(e).getId(), graph.getEdgeTarget(e).getId(), graph.getEdgeWeight(e)))
                .collect(Collectors.toList());

        GraphDTO graphDTO = new GraphDTO(nodes, edges);

        return graphDTO;
    }
}
