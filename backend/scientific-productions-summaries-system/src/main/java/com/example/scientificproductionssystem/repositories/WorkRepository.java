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

//    @Query("SELECT w FROM Work w WHERE (:institute is null OR w.researcher.institute = :institute) AND " +
//            "(:researcher is null OR w.researcher = :researcher) AND " +
//            "(:startYear is null OR w.year >= :startYear) AND " +
//            "(:endYear is null OR w.year <= :endYear) AND " +
//            "(:workType is null OR :workType = 'all') OR " +
//            "((CASE WHEN TYPE(w) = Book THEN 'book' ELSE 'article' END) = :workType)")
//    Page<Work> findAllByDateOrInstituteOrResearcherOrType(
//            @Param("institute") Institute institute,
//            @Param("researcher") Researcher researcher,
//            @Param("startYear") Integer startYear,
//            @Param("endYear") Integer endYear,
//            @Param("workType") String workType,
//            Pageable pageable
//    );
}
