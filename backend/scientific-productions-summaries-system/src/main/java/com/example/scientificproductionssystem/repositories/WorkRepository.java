package com.example.scientificproductionssystem.repositories;

import com.example.scientificproductionssystem.model.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Long> {
    Page<Work> findByResearcherIdAndYearBetween(Long researcherId, Integer startYear, Integer endYear, Pageable pageable);
    Page<Work> findByYearBetween(Integer startYear, Integer endYear, Pageable pageable);
}