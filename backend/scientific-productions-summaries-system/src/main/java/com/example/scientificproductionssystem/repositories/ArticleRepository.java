package com.example.scientificproductionssystem.repositories;

import com.example.scientificproductionssystem.model.Work;
import com.example.scientificproductionssystem.model.worktypes.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Work> findByResearcherIdAndYearBetween(Long researcherId, Integer startYear, Integer endYear, Pageable pageable);
    Page<Work> findByYearBetween(Integer startYear, Integer endYear, Pageable pageable);
    Page<Work> findByResearcherInstituteIdAndYearBetween(Long instituteId, Integer startYear, Integer endYear, Pageable pageable);
    Page<Work> findByResearcherIdAndResearcherInstituteIdAndYearBetween(Long researcherId, Long instituteId, Integer startYear, Integer endYear, Pageable pageable);

}
