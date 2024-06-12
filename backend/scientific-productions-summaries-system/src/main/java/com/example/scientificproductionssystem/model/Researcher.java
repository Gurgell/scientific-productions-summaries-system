package com.example.scientificproductionssystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "researcher")
public class Researcher implements Serializable {
    @Id
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

    @OneToMany(mappedBy = "researcher", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Nullable
    private List<Work> works;

    @Column(name = "available_quote_names")
    private String availableQuoteNames;

    public Researcher(){}

    public Researcher(Long id, String name, String email, Institute institute, @Nullable List<Work> works, String availableQuoteNames) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.institute = institute;
        this.works = works;
        this.availableQuoteNames = availableQuoteNames;

        if (works != null){
            for (Work work : works) {
                work.setResearcher(this);
                work.getQuoteNames().forEach(quoteName -> quoteName.setWork(work));
            }
        }
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

    public String getAvailableQuoteNames() {
        return availableQuoteNames;
    }

    public void setAvailableQuoteNames(String availableQuoteNames) {
        this.availableQuoteNames = availableQuoteNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Researcher that = (Researcher) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(institute, that.institute) && Objects.equals(works, that.works) && Objects.equals(availableQuoteNames, that.availableQuoteNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, institute, works, availableQuoteNames);
    }
}
