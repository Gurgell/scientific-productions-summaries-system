package com.example.scientificproductionssystem.services;

import com.example.scientificproductionssystem.dto.researcher.ResearcherDetailsDTO;
import com.example.scientificproductionssystem.dto.researcher.ResearcherUpdateDTO;
import com.example.scientificproductionssystem.exceptions.RequiredObjectIsNullException;
import com.example.scientificproductionssystem.exceptions.ResourceNotFoundException;
import com.example.scientificproductionssystem.mapper.ResearcherMapper;
import com.example.scientificproductionssystem.model.Institute;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.repositories.InstituteRepository;
import com.example.scientificproductionssystem.repositories.ResearcherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResearcherService {

    @Autowired
    ResearcherRepository repository;

    @Autowired
    InstituteRepository instituteRepository;

    @Autowired
    ResearcherMapper researcherMapper;

    public ResearcherDetailsDTO findById(Long id){
        Researcher researcher = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        return researcherMapper.toResearcherDetailsDTO(researcher);
    }

    public ResearcherDetailsDTO create(ResearcherUpdateDTO researcherUpdateDTO) {
        if (researcherUpdateDTO == null) throw new RequiredObjectIsNullException();
        Institute institute = instituteRepository.findById(researcherUpdateDTO.getInstitute_id()).orElseThrow(() -> new RequiredObjectIsNullException("No records found for this institute ID!"));

        Researcher researcher = researcherMapper.fromResearcherUpdateDTOToResearcher(researcherUpdateDTO);
        researcher.setInstitute(institute);

        repository.save(researcher);

        return researcherMapper.toResearcherDetailsDTO(researcher);
    }

    public void delete(Long id){
        ResearcherDetailsDTO researcherDetailsDTO = this.findById(id);

        Researcher researcher = researcherMapper.fromResearcherDetailsDTOToResearcher(researcherDetailsDTO);
        repository.delete(researcher);
    }

    public List<ResearcherDetailsDTO> findAll() {
        List<Researcher> researchers = repository.findAll();
        if (researchers.isEmpty()) throw new ResourceNotFoundException("No researchers found!");

        return researcherMapper.fromListResearchersToResearchersDetailsDTO(researchers);
    }

    public ResearcherDetailsDTO update(ResearcherUpdateDTO researcherUpdateDTO, Long id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this researcher ID!"));
        Institute institute = instituteRepository.findById(researcherUpdateDTO.getInstitute_id()).orElseThrow(() -> new ResourceNotFoundException("No records found for this institute ID!"));

        Researcher researcher = researcherMapper.fromResearcherUpdateDTOToResearcher(researcherUpdateDTO);
        researcher.setId(id);
        researcher.setInstitute(institute);

        repository.save(researcher);

        return researcherMapper.toResearcherDetailsDTO(researcher);
    }
}
