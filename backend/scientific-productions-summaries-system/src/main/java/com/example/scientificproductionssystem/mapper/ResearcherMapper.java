package com.example.scientificproductionssystem.mapper;

import com.example.scientificproductionssystem.dto.institute.InstituteDetailsDTO;
import com.example.scientificproductionssystem.dto.researcher.ResearcherDetailsDTO;
import com.example.scientificproductionssystem.dto.researcher.ResearcherUpdateDTO;
import com.example.scientificproductionssystem.model.Institute;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.repositories.InstituteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResearcherMapper {

    @Autowired
    private ModelMapper mapper;

    public Researcher fromResearcherUpdateDTOToResearcher(ResearcherUpdateDTO researcherUpdateDTO){
        return new Researcher(
                researcherUpdateDTO.getId(),
                researcherUpdateDTO.getName(),
                researcherUpdateDTO.getEmail(),
                null,
                researcherUpdateDTO.getWorks());
    }

    public Researcher fromResearcherDetailsDTOToResearcher(ResearcherDetailsDTO researcherDetailsDTO){
        return new Researcher(
                researcherDetailsDTO.getId(),
                researcherDetailsDTO.getName(),
                researcherDetailsDTO.getEmail(),
                researcherDetailsDTO.getInstitute(),
                researcherDetailsDTO.getWorks());
    }

    public ResearcherUpdateDTO toResearcherUpdateDTO(Researcher researcher){
        return new ResearcherUpdateDTO(
                researcher.getId(),
                researcher.getName(),
                researcher.getEmail(),
                researcher.getWorks()
        );
    }

    public ResearcherDetailsDTO toResearcherDetailsDTO(Researcher researcher){
        return new ResearcherDetailsDTO(
                researcher.getId(),
                researcher.getName(),
                researcher.getEmail(),
                researcher.getInstitute(),
                researcher.getWorks()
        );
    }

    public List<ResearcherDetailsDTO> fromListResearchersToResearchersDetailsDTO(List<Researcher> researchers){
        List<ResearcherDetailsDTO> researcherDetailsDTOS = new ArrayList<>();
        for (Researcher researcher : researchers) {
            researcherDetailsDTOS.add(toResearcherDetailsDTO(researcher));
        }
        return researcherDetailsDTOS;
    }

    public Page<ResearcherDetailsDTO> fromPageResearchersToResearchersDetailsDTO(Page<Researcher> researchers){
        return researchers.map(this::toResearcherDetailsDTO);
    }
}
