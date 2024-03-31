package com.example.scientificproductionssystem.repositories;

import com.example.scientificproductionssystem.model.Institute;
import com.example.scientificproductionssystem.model.Researcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResearcherRepository extends JpaRepository<Researcher, Long> {
    Page<Researcher> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Researcher> findByEmailContainingIgnoreCase(String email, Pageable pageable);
}
