package com.example.scientificproductionssystem.services;

import com.example.scientificproductionssystem.dto.researcher.ResearcherDetailsDTO;
import com.example.scientificproductionssystem.dto.work.WorkDetailsDTO;
import com.example.scientificproductionssystem.exceptions.ResourceNotFoundException;
import com.example.scientificproductionssystem.mapper.WorkMapper;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.model.Work;
import com.example.scientificproductionssystem.repositories.ArticleRepository;
import com.example.scientificproductionssystem.repositories.BookRepository;
import com.example.scientificproductionssystem.repositories.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkService {
    @Autowired
    WorkRepository repository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    WorkMapper workMapper;

    public WorkDetailsDTO findById(Long id){
        Work work = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        return workMapper.toWorkDetailsDTO(work);
    }

    public List<WorkDetailsDTO> findAll() {
        List<Work> works = repository.findAll();
        if (works.isEmpty()) throw new ResourceNotFoundException("No researchers found!");

        return workMapper.fromListWorksToWorksDetailsDTO(works);
    }

    public Page<WorkDetailsDTO> findWithParams(Integer page, Integer limit, Integer startYear, Integer endYear, Optional<Long> idInstitute, Optional<Long> idResearcher, String type) {
        Page<Work> works;

        if(!type.equals("all") && !type.equals("book") && !type.equals("article"))
            throw new ResourceNotFoundException("Type informed does not exist");

        Pageable pageable = PageRequest.of(page, limit, Sort.unsorted());

        if (idInstitute.isPresent() && idResearcher.isPresent()) {
           if(type.equals("book"))
                works = bookRepository.findByResearcherIdAndYearBetween(idResearcher.get(), startYear, endYear, pageable);

            else if(type.equals("article"))
                works = articleRepository.findByResearcherIdAndYearBetween(idResearcher.get(), startYear, endYear, pageable);

            else //all
                works = repository.findByResearcherIdAndYearBetween(idResearcher.get(), startYear, endYear, pageable);
        }

//        else if (idInstitute.isPresent()) {
//            if(type.equals("all"))
//
//            else if(type.equals("book"))
//
//            else if(type.equals("article"))
//        }

        else if(idResearcher.isPresent()){
            if(type.equals("book"))
                works = bookRepository.findByResearcherIdAndYearBetween(idResearcher.get(), startYear, endYear, pageable);

            else if(type.equals("article"))
                works = articleRepository.findByResearcherIdAndYearBetween(idResearcher.get(), startYear, endYear, pageable);

            else //all
                works = repository.findByResearcherIdAndYearBetween(idResearcher.get(), startYear, endYear, pageable);
        }

        else {  //idResearcher.isEmpty() && idInstitute.isEmpty()
            if(type.equals("book"))
                works = bookRepository.findByYearBetween(startYear, endYear, pageable);

            else if(type.equals("article"))
                works = articleRepository.findByYearBetween(startYear, endYear, pageable);

            else //all
                works = repository.findByYearBetween(startYear, endYear, pageable);
        }

        if (works.isEmpty()) throw new ResourceNotFoundException("No works found!");

        return workMapper.fromPageWorksToWorksDetailsDTO(works);
    }
}
