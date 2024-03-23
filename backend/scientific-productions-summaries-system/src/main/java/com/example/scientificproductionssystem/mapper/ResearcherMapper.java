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
        return mapper.map(researcherUpdateDTO, Researcher.class);
    }

    public Researcher fromResearcherDetailsDTOToResearcher(ResearcherDetailsDTO researcherDetailsDTO){
        return mapper.map(researcherDetailsDTO, Researcher.class);
    }

    public ResearcherUpdateDTO toResearcherUpdateDTO(Researcher researcher){
        return mapper.map(researcher, ResearcherUpdateDTO.class);
    }

    public ResearcherDetailsDTO toResearcherDetailsDTO(Researcher researcher){
        return mapper.map(researcher, ResearcherDetailsDTO.class);
    }

    public List<ResearcherDetailsDTO> fromListResearchersToResearchersDetailsDTO(List<Researcher> researchers){
        List<ResearcherDetailsDTO> researcherDetailsDTO = new ArrayList<>();
        for (Researcher researcher : researchers) {
            researcherDetailsDTO.add(this.toResearcherDetailsDTO(researcher));
        }

        return researcherDetailsDTO;
    }

    public Page<ResearcherDetailsDTO> fromPageResearchersToResearchersDetailsDTO(Page<Researcher> researchers){
        return researchers.map(this::toResearcherDetailsDTO);
    }
}
