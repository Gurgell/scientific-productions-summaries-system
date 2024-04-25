package com.example.scientificproductionssystem.dto.researcher;

import com.example.scientificproductionssystem.model.Institute;
import com.example.scientificproductionssystem.model.Work;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ResearcherDetailsDTO implements Serializable {

    private Long id;

    private String name;

    private String email;

    private Institute institute;

    @Nullable
    private List<Work> works;

    public ResearcherDetailsDTO(){}

    public ResearcherDetailsDTO(Long id, String name, String email, Institute institute, @Nullable List<Work> works) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.institute = institute;
        this.works = works;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    @Nullable
    public List<Work> getWorks() {
        return works;
    }

    public void setWorks(@Nullable List<Work> works) {
        this.works = works;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResearcherDetailsDTO that = (ResearcherDetailsDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(institute, that.institute) && Objects.equals(works, that.works);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, institute, works);
    }
}
