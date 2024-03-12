package com.example.scientificproductionssystem.repositories;

import com.example.scientificproductionssystem.model.Institute;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstituteRepository extends JpaRepository<Institute, Long> {
    List<Institute> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Institute> findByAcronymStartingWithIgnoreCase(String acronym, Pageable pageable);
}
