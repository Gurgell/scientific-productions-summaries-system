package com.example.scientificproductionssystem.model;

import com.example.scientificproductionssystem.mapper.ResearcherMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Year;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "WORK_TYPE")
@Table(name = "WORK")
public abstract class Work implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer year;

    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    private List<QuoteName> quoteNames;

    @ManyToOne
    @JoinColumn(name = "researcher_id")
    @JsonIgnore
    private Researcher researcher;

    public Work(){}

    public Work(Long id, String title, Integer year, List<QuoteName> quoteNames, Researcher researcher) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.quoteNames = quoteNames;
        this.researcher = researcher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<QuoteName> getQuoteNames() {
        return quoteNames;
    }

    public void setQuoteNames(List<QuoteName> quoteNames) {
        this.quoteNames = quoteNames;
    }

    public Researcher getResearcher() {
        return researcher;
    }

    public void setResearcher(Researcher researcher) {
        this.researcher = researcher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Work work = (Work) o;
        return Objects.equals(id, work.id) && Objects.equals(title, work.title) && Objects.equals(year, work.year) && Objects.equals(quoteNames, work.quoteNames) && Objects.equals(researcher, work.researcher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, year, quoteNames, researcher);
    }
}
