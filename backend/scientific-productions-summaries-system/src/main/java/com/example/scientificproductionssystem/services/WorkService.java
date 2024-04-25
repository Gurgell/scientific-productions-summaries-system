package com.example.scientificproductionssystem.services;

import com.example.scientificproductionssystem.dto.researcher.ResearcherDetailsDTO;
import com.example.scientificproductionssystem.dto.work.WorkDetailsDTO;
import com.example.scientificproductionssystem.exceptions.ResourceNotFoundException;
import com.example.scientificproductionssystem.mapper.WorkMapper;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.model.Work;
import com.example.scientificproductionssystem.repositories.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkService {
    @Autowired
    WorkRepository repository;

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
}
