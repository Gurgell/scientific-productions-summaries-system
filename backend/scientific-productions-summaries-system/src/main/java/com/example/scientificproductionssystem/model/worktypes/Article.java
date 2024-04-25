package com.example.scientificproductionssystem.model.worktypes;

import com.example.scientificproductionssystem.model.QuoteName;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.model.Work;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.Year;
import java.util.List;
import java.util.Objects;

@Entity
@DiscriminatorValue("ARTICLE")
public class Article extends Work {

    private String place;

    public Article() {}

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Article article = (Article) o;
        return Objects.equals(place, article.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), place);
    }
}
