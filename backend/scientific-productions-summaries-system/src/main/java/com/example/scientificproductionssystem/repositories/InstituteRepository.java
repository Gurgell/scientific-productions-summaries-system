package com.example.scientificproductionssystem.repositories;

import com.example.scientificproductionssystem.model.Institute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstituteRepository extends JpaRepository<Institute, Long> {
    Page<Institute> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Institute> findByAcronymStartingWithIgnoreCase(String acronym, Pageable pageable);
}
