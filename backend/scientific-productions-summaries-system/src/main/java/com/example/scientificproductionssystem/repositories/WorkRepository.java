package com.example.scientificproductionssystem.repositories;

import com.example.scientificproductionssystem.model.Institute;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.model.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    Page<Work> findByResearcherIdAndYearBetween(Long researcherId, Integer startYear, Integer endYear, Pageable pageable);
    Page<Work> findByYearBetween(Integer startYear, Integer endYear, Pageable pageable);
    Page<Work> findByResearcherInstituteIdAndYearBetween(Long instituteId, Integer startYear, Integer endYear, Pageable pageable);
    Page<Work> findByResearcherIdAndResearcherInstituteIdAndYearBetween(Long researcherId, Long instituteId, Integer startYear, Integer endYear, Pageable pageable);
}
