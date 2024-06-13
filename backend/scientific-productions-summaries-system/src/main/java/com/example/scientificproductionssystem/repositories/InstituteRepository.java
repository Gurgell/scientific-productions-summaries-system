package com.example.scientificproductionssystem.repositories;

import com.example.scientificproductionssystem.model.Institute;
import com.example.scientificproductionssystem.model.Researcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstituteRepository extends JpaRepository<Institute, Long> {
    Page<Institute> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Institute> findByAcronymStartingWithIgnoreCase(String acronym, Pageable pageable);
    Page<Institute> findByNameContainingIgnoreCaseOrAcronymStartingWithIgnoreCase(String term, String term2, Pageable pageable);

    @Query("SELECT DISTINCT i FROM Institute i JOIN FETCH i.researchers r WHERE r IN :researchers")
    List<Institute> findAllByResearchers(@Param("researchers") List<Researcher> researchers);
}
