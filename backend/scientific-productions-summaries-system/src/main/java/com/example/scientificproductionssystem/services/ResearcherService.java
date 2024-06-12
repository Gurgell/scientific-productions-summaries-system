package com.example.scientificproductionssystem.services;

import com.example.scientificproductionssystem.dto.institute.InstituteDetailsDTO;
import com.example.scientificproductionssystem.dto.researcher.ResearcherDetailsDTO;
import com.example.scientificproductionssystem.dto.researcher.ResearcherUpdateDTO;
import com.example.scientificproductionssystem.exceptions.RequiredObjectIsNullException;
import com.example.scientificproductionssystem.exceptions.ResourceNotFoundException;
import com.example.scientificproductionssystem.mapper.ResearcherMapper;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.model.Work;
import com.example.scientificproductionssystem.repositories.InstituteRepository;
import com.example.scientificproductionssystem.repositories.ResearcherRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

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

    public ResearcherDetailsDTO findById(Long id) {
        Researcher researcher = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        return researcherMapper.toResearcherDetailsDTO(researcher);
    }

    public ResearcherUpdateDTO findResearcherByCurriculumId(Long curriculumId) {

        ResearcherUpdateDTO researcherUpdateDTO = new ResearcherUpdateDTO();

        researcherUpdateDTO.setId(curriculumId);
        researcherUpdateDTO.setName(xmlSearchService.findResearcherName(curriculumId));

        //Retirando acentos para utilizar o nome no e-mail
        String email = Normalizer.normalize(researcherUpdateDTO.getName(), Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        researcherUpdateDTO.setEmail(email.replace(" ", "_").strip().toLowerCase() + "@gmail.com");

        return researcherUpdateDTO;
    }

    public ResearcherDetailsDTO create(ResearcherDetailsDTO researcherDetailsDTO) {
        if (researcherDetailsDTO == null) throw new RequiredObjectIsNullException();
        instituteRepository.findById(researcherDetailsDTO.getInstitute().getId()).orElseThrow(() -> new RequiredObjectIsNullException("No records found for this institute ID!"));

        if (repository.findById(researcherDetailsDTO.getId()).isPresent()) {
            throw new EntityExistsException("This researcher ID is already registered on the system!");
        }

        researcherDetailsDTO.setWorks(xmlSearchService.findResearcherWorks(researcherDetailsDTO.getId()));

        Researcher researcher = researcherMapper.fromResearcherDetailsDTOToResearcher(researcherDetailsDTO);

        return researcherMapper.toResearcherDetailsDTO(repository.save(researcher));
    }

    public void delete(Long id) {
        ResearcherDetailsDTO researcherDetailsDTO = this.findById(id);

        Researcher researcher = researcherMapper.fromResearcherDetailsDTOToResearcher(researcherDetailsDTO);
        repository.delete(researcher);
    }

    public List<ResearcherDetailsDTO> findAll() {
        List<Researcher> researchers = repository.findAll();
        if (researchers.isEmpty()) throw new ResourceNotFoundException("No researchers found!");

        return researcherMapper.fromListResearchersToResearchersDetailsDTO(researchers);
    }

    public Page<ResearcherDetailsDTO> findWithParams(Integer page, Integer limit, Optional<String> field, Optional<String> term) {
        Page<Researcher> researchers;

        if (field.isEmpty() && term.isEmpty()) {
            Pageable pageable = PageRequest.of(page, limit, Sort.unsorted());
            researchers = repository.findAll(pageable);
        } else if (field.isEmpty() || term.isEmpty()) {
            throw new ResourceNotFoundException("The field and term should be passed together!");
        } else {
            PageRequest pageRequest = PageRequest.of(page, limit, Sort.unsorted());
            if (field.get().equals("all"))
                researchers = repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrInstitute_NameContainingIgnoreCase(term.get(), term.get(), term.get(), pageRequest);
            else if (field.get().equals("name"))
                researchers = repository.findByNameContainingIgnoreCase(term.get(), pageRequest);
            else if (field.get().equals("email"))
                researchers = repository.findByEmailContainingIgnoreCase(term.get(), pageRequest);
            else if (field.get().equals("institute"))
                researchers = repository.findByInstitute_NameContainingIgnoreCase(term.get(), pageRequest);
            else throw new ResourceNotFoundException("Search field wrongly informed!");
        }

        if (researchers.isEmpty()) throw new ResourceNotFoundException("No researchers found!");

        return researcherMapper.fromPageResearchersToResearchersDetailsDTO(researchers);
    }

    public List<ResearcherDetailsDTO> findAllById(List<Long> ids) {
        return researcherMapper.fromListResearchersToResearchersDetailsDTO(repository.findAllById(ids));
    }
}
