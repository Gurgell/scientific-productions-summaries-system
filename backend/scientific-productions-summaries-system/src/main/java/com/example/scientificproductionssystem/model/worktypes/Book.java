package com.example.scientificproductionssystem.model.worktypes;

import com.example.scientificproductionssystem.model.QuoteName;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.model.Work;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.Year;
import java.util.List;

@Entity
@DiscriminatorValue("BOOK")
public class Book extends Work {
    public Book() {
    }

    public Book(Long id, String title, Integer year, List<QuoteName> quoteNames, Researcher researcher) {
        super(id, title, year, quoteNames, researcher);
    }

}
