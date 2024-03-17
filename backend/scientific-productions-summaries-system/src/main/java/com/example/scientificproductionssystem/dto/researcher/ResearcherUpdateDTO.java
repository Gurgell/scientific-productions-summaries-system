package com.example.scientificproductionssystem.dto.researcher;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

public class ResearcherUpdateDTO implements Serializable {

    private Long id;

    private Long institute_id;

    public ResearcherUpdateDTO() {
    }

    public ResearcherUpdateDTO(Long id, Long institute_id) {
        this.id = id;
        this.institute_id = institute_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return Objects.equals(id, that.id) && Objects.equals(institute_id, that.institute_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, institute_id);
    }
}
