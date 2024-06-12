package com.example.scientificproductionssystem.repositories;

import com.example.scientificproductionssystem.model.QuoteName;
import com.example.scientificproductionssystem.model.Work;
import com.example.scientificproductionssystem.model.worktypes.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuoteNameRepository extends JpaRepository<QuoteName, Long> {

    @Query("SELECT q FROM QuoteName q WHERE q.name = :quoteName")
    List<QuoteName> findByName(String quoteName);
}
