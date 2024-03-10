package com.example.scientificproductionssystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "institute")
public class Institute implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotBlank
    private String name;
    @Column(nullable = false)
    @NotBlank
    private String acronym;

    @JsonIgnore
    @OneToMany(mappedBy = "institute")
    private List<Researcher> researchers;

    public Institute(){}

    public Institute(Long id, String name, String acronym) {
        this.id = id;
        this.name = name;
        this.acronym = acronym;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public List<Researcher> getResearchers() {
        return researchers;
    }

    public void setResearchers(List<Researcher> researchers) {
        this.researchers = researchers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Institute institute = (Institute) o;
        return Objects.equals(id, institute.id) && Objects.equals(name, institute.name) && Objects.equals(acronym, institute.acronym) && Objects.equals(researchers, institute.researchers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, acronym, researchers);
    }
}
