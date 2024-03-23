package com.example.scientificproductionssystem.services;

import com.example.scientificproductionssystem.dto.institute.InstituteDetailsDTO;
import com.example.scientificproductionssystem.dto.researcher.ResearcherDetailsDTO;
import com.example.scientificproductionssystem.dto.researcher.ResearcherUpdateDTO;
import com.example.scientificproductionssystem.exceptions.RequiredObjectIsNullException;
import com.example.scientificproductionssystem.exceptions.ResourceNotFoundException;
import com.example.scientificproductionssystem.mapper.ResearcherMapper;
import com.example.scientificproductionssystem.model.Institute;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.repositories.InstituteRepository;
import com.example.scientificproductionssystem.repositories.ResearcherRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.List;

@Service
public class ResearcherService {

    @Autowired
    ResearcherRepository repository;

    @Autowired
    InstituteRepository instituteRepository;

    @Autowired
    ResearcherMapper researcherMapper;

    @Autowired
    XmlSearchService xmlSearchService;

    public ResearcherDetailsDTO findById(Long id){
        Researcher researcher = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        return researcherMapper.toResearcherDetailsDTO(researcher);
    }

    public ResearcherDetailsDTO create(ResearcherUpdateDTO researcherUpdateDTO) {
        if (researcherUpdateDTO == null) throw new RequiredObjectIsNullException();
        Institute institute = instituteRepository.findById(researcherUpdateDTO.getInstitute_id()).orElseThrow(() -> new RequiredObjectIsNullException("No records found for this institute ID!"));
        Researcher researcher = researcherMapper.fromResearcherUpdateDTOToResearcher(researcherUpdateDTO);

        researcher.setId(researcherUpdateDTO.getId());
        String name = xmlSearchService.findResearcherCurriculum(researcher.getId());
        researcher.setName(name);

        //Retirando acentos para utilizar o nome no e-mail
        String email = Normalizer.normalize(researcher.getName(), Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        researcher.setEmail(email.replace(" ", "_").strip().toLowerCase() + "@gmail.com");

        researcher.setInstitute(institute);

        if (repository.findById(researcher.getId()).isPresent()){
            throw new EntityExistsException("This researcher ID already exists!");
        }
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

    public Page<ResearcherDetailsDTO> findWithParams(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.unsorted());
        Page<Researcher> researchers = repository.findAll(pageable);
        if (researchers.isEmpty()) throw new ResourceNotFoundException("No researchers found!");

        return researcherMapper.fromPageResearchersToResearchersDetailsDTO(researchers);
    }

    public Page<ResearcherDetailsDTO> findWithParams(Integer page, Integer limit, String field, String value) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.unsorted());

        Page<Researcher> researchers;
        if(field.equals("name"))
            researchers = repository.findByNameContainingIgnoreCase(value, pageRequest);
        else if(field.equals("email"))
            researchers = repository.findByEmailStartingWithIgnoreCase(value, pageRequest);
        else throw new ResourceNotFoundException("Search field wrongly informed!");

        if (researchers.isEmpty()) throw new ResourceNotFoundException("No institutes found!");

        return researcherMapper.fromPageResearchersToResearchersDetailsDTO(researchers);
    }

}
