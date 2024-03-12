package com.example.scientificproductionssystem.mapper;

import com.example.scientificproductionssystem.dto.institute.InstituteDetailsDTO;
import com.example.scientificproductionssystem.dto.institute.InstituteUpdateDTO;
import com.example.scientificproductionssystem.model.Institute;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstituteMapper {
    
    @Autowired
    private ModelMapper mapper;

    public Institute fromInstituteUpdateDTOToInstitute(InstituteUpdateDTO InstituteUpdateDTO){
        return mapper.map(InstituteUpdateDTO, Institute.class);
    }

    public Institute fromInstituteDetailsDTOToInstitute(InstituteDetailsDTO instituteDetailsDTO){
        return mapper.map(instituteDetailsDTO, Institute.class);
    }

    public InstituteUpdateDTO toInstituteUpdateDTO(Institute institute){
        return mapper.map(institute, InstituteUpdateDTO.class);
    }

    public InstituteDetailsDTO toInstituteDetailsDTO(Institute institute){
        return mapper.map(institute, InstituteDetailsDTO.class);
    }

    public List<InstituteDetailsDTO> fromListInstitutesToInstitutesDetailsDTO(List<Institute> institutes){
        List<InstituteDetailsDTO> instituteDetailsDTO = new ArrayList<>();
        for (Institute institute : institutes) {
            instituteDetailsDTO.add(this.toInstituteDetailsDTO(institute));
        }

        return instituteDetailsDTO;
    }

    public List<InstituteDetailsDTO> fromPageInstitutesToInstitutesDetailsDTO(Page<Institute> institutes){
        List<InstituteDetailsDTO> instituteDetailsDTO = new ArrayList<>();
        for (Institute institute : institutes) {
            instituteDetailsDTO.add(this.toInstituteDetailsDTO(institute));
        }

        return instituteDetailsDTO;
    }
}
