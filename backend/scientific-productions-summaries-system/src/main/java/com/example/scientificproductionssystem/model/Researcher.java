package com.example.scientificproductionssystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "researcher")
public class Researcher implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String email;

    @JoinColumn(name = "institute_id", nullable = false)
    @ManyToOne
    @NotNull
    private Institute institute;

    public Researcher(){}

    public Researcher(Long id, String name, String email, Institute institute) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.institute = institute;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Researcher that = (Researcher) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(institute, that.institute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, institute);
    }
}
