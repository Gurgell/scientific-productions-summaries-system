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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public InstituteDetailsDTO update(InstituteUpdateDTO instituteUpdateDTO, Long id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this institute ID!"));

        Institute institute = instituteMapper.fromInstituteUpdateDTOToInstitute(instituteUpdateDTO);
        institute.setId(id);
        repository.save(institute);

        return instituteMapper.toInstituteDetailsDTO(institute);
    }
}
