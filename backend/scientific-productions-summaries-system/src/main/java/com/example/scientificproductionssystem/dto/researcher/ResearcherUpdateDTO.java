package com.example.scientificproductionssystem.dto.researcher;

import com.example.scientificproductionssystem.model.Work;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ResearcherUpdateDTO implements Serializable {

    private Long id;

    private String name;

    private String email;

    @Nullable
    @JsonIgnore
    private List<Work> works;

    public ResearcherUpdateDTO() {
    }

    public ResearcherUpdateDTO(Long id, String name, String email, @Nullable List<Work> works) {
        this.id = id;
        this.name = name;
        this.email = email;
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
        ResearcherUpdateDTO that = (ResearcherUpdateDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(works, that.works);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, works);
    }
}
