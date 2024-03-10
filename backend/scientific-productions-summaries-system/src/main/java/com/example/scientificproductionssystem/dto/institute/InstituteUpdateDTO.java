package com.example.scientificproductionssystem.dto.institute;

import java.io.Serializable;
import java.util.Objects;

public class InstituteUpdateDTO implements Serializable {

    private String name;

    private String acronym;

    public InstituteUpdateDTO() {
    }

    public InstituteUpdateDTO(String name, String acronym) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstituteUpdateDTO that = (InstituteUpdateDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(acronym, that.acronym);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, acronym);
    }
}
