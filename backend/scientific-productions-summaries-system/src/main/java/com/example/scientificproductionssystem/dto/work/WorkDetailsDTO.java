package com.example.scientificproductionssystem.dto.work;

import java.io.Serializable;
import java.util.Objects;

public class WorkDetailsDTO implements Serializable {
    private Long id;
    private String type;
    private String Details;

    public WorkDetailsDTO(){}

    public WorkDetailsDTO(Long id, String type, String details) {
        this.id = id;
        this.type = type;
        Details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkDetailsDTO that = (WorkDetailsDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(type, that.type) && Objects.equals(Details, that.Details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, Details);
    }
}
