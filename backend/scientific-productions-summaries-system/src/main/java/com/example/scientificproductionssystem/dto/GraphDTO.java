package com.example.scientificproductionssystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class GraphDTO implements Serializable {
    private List<Vertex> nodes;
    private List<Edge> edges;

    public GraphDTO(List<Vertex> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public List<Vertex> getNodes() {
        return nodes;
    }

    public void setNodes(List<Vertex> nodes) {
        this.nodes = nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public static class Vertex {
        private Long id;

        private String label;

        public Vertex(Long id, String label) {
            this.id = id;
            this.label = label;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

    }

    public static class Edge {
        private Long source;
        private Long target;
        private Double weight;


        public Edge(Long source, Long target, Double weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }

        public Long getSource() {
            return source;
        }

        public void setSource(Long source) {
            this.source = source;
        }

        public Long getTarget() {
            return target;
        }

        public void setTarget(Long target) {
            this.target = target;
        }

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }
    }
}
