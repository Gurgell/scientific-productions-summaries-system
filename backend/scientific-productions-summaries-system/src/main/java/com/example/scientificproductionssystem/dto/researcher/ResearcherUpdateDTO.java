package com.example.scientificproductionssystem.dto.researcher;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

public class ResearcherUpdateDTO implements Serializable {

    private String name;

    private String email;

    private Long institute_id;

    public ResearcherUpdateDTO(){}
    public ResearcherUpdateDTO(String name, String email, Long institute_id) {
        this.name = name;
        this.email = email;
        this.institute_id = institute_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(Long institute_id) {
        this.institute_id = institute_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResearcherUpdateDTO that = (ResearcherUpdateDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(institute_id, that.institute_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, institute_id);
    }
}
