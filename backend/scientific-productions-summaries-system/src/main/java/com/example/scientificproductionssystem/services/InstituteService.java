package com.example.scientificproductionssystem.services;

import com.example.scientificproductionssystem.dto.institute.InstituteDetailsDTO;
import com.example.scientificproductionssystem.dto.institute.InstituteUpdateDTO;
import com.example.scientificproductionssystem.exceptions.RequiredObjectIsNullException;
import com.example.scientificproductionssystem.exceptions.ResourceNotFoundException;
import com.example.scientificproductionssystem.mapper.InstituteMapper;
import com.example.scientificproductionssystem.model.Institute;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.repositories.InstituteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstituteService {

    @Autowired
    InstituteRepository repository;

    @Autowired
    InstituteMapper instituteMapper;

    public InstituteDetailsDTO findById(Long id){
        Institute institute = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        return instituteMapper.toInstituteDetailsDTO(institute);
    }

    public InstituteDetailsDTO create(InstituteUpdateDTO instituteUpdateDTO) {
        if (instituteUpdateDTO == null) throw new RequiredObjectIsNullException();

        Institute institute = instituteMapper.fromInstituteUpdateDTOToInstitute(instituteUpdateDTO);

        repository.save(institute);

        return instituteMapper.toInstituteDetailsDTO(institute);
    }

    public void delete(Long id){
        InstituteDetailsDTO instituteDetailsDTO = this.findById(id);

        Institute institute = instituteMapper.fromInstituteDetailsDTOToInstitute(instituteDetailsDTO);

        repository.delete(institute);
    }

    public List<InstituteDetailsDTO> findAll() {
        List<Institute> institutes = repository.findAll();
        if (institutes.isEmpty()) throw new ResourceNotFoundException("No institutes found!");

        return instituteMapper.fromListInstitutesToInstitutesDetailsDTO(institutes);
    }

    public List<InstituteDetailsDTO> findWithParams(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.unsorted());
        Page<Institute> institutes = repository.findAll(pageable);
        if (institutes.isEmpty()) throw new ResourceNotFoundException("No institutes found!");

        return instituteMapper.fromPageInstitutesToInstitutesDetailsDTO(institutes);
    }

    public List<InstituteDetailsDTO> findWithParams(Integer page, Integer limit, String field, String value) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.unsorted());

        List<Institute> institutes;
        if(field.equals("name"))
            institutes = repository.findByNameContainingIgnoreCase(value, pageRequest);
        else if(field.equals("acronym"))
            institutes = repository.findByAcronymStartingWithIgnoreCase(value, pageRequest);
        else throw new ResourceNotFoundException("Search field wrongly informed!");

        if (institutes.isEmpty()) throw new ResourceNotFoundException("No institutes found!");

        return instituteMapper.fromListInstitutesToInstitutesDetailsDTO(institutes);
    }

    public InstituteDetailsDTO update(InstituteUpdateDTO instituteUpdateDTO, Long id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this institute ID!"));

        Institute institute = instituteMapper.fromInstituteUpdateDTOToInstitute(instituteUpdateDTO);
        institute.setId(id);
        repository.save(institute);

        return instituteMapper.toInstituteDetailsDTO(institute);
    }
}
