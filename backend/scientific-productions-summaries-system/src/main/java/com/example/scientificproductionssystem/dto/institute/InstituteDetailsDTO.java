package com.example.scientificproductionssystem.dto.institute;

import com.example.scientificproductionssystem.model.Institute;

import java.io.Serializable;
import java.util.Objects;

public class InstituteDetailsDTO implements Serializable {
    private Long id;
    private String name;
    private String acronym;

    public InstituteDetailsDTO() {
    }

    public InstituteDetailsDTO(Long id, String name, String acronym) {
        this.id = id;
        this.name = name;
        this.acronym = acronym;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstituteDetailsDTO that = (InstituteDetailsDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(acronym, that.acronym);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, acronym);
    }
}
