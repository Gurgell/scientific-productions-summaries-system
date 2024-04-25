package com.example.scientificproductionssystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.jfr.Enabled;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "QUOTE_NAME")
public class QuoteName implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "work_id")
    @JsonIgnore
    private Work work;

    public QuoteName(){}

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

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuoteName quoteName = (QuoteName) o;
        return Objects.equals(id, quoteName.id) && Objects.equals(name, quoteName.name) && Objects.equals(work, quoteName.work);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, work);
    }
}
