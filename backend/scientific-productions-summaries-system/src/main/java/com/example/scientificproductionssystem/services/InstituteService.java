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

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Long countAll(){
        return repository.count();
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

//    public Page<InstituteDetailsDTO> findWithParams(Integer page, Integer limit) {
//        Pageable pageable = PageRequest.of(page, limit, Sort.unsorted());
//        Page<Institute> institutes = repository.findAll(pageable);
//        if (institutes.isEmpty()) throw new ResourceNotFoundException("No institutes found!");
//
//        return instituteMapper.fromPageInstitutesToInstitutesDetailsDTO(institutes);
//    }

    public Page<InstituteDetailsDTO> findWithParams(Integer page, Integer limit, Optional<String> field, Optional<String> term) {
        Page<Institute> institutes;

        if(field.isEmpty() && term.isEmpty()){
            Pageable pageable = PageRequest.of(page, limit, Sort.unsorted());
            institutes = repository.findAll(pageable);
        }
        else if(field.isEmpty() || term.isEmpty()){
            throw new ResourceNotFoundException("The field and term should be passed together!");
        }
        else{
            PageRequest pageRequest = PageRequest.of(page, limit, Sort.unsorted());
            if(field.get().equals("all"))
                institutes = repository.findByNameContainingIgnoreCaseOrAcronymStartingWithIgnoreCase(term.get(), term.get(), pageRequest);
            else if(field.get().equals("name"))
                institutes = repository.findByNameContainingIgnoreCase(term.get(), pageRequest);
            else if(field.get().equals("acronym"))
                institutes = repository.findByAcronymStartingWithIgnoreCase(term.get(), pageRequest);
            else throw new ResourceNotFoundException("Search field wrongly informed!");
        }

        if (institutes.isEmpty()) throw new ResourceNotFoundException("No institutes found!");

        return instituteMapper.fromPageInstitutesToInstitutesDetailsDTO(institutes);
    }

    public InstituteDetailsDTO update(InstituteUpdateDTO instituteUpdateDTO, Long id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this institute ID!"));

        Institute institute = instituteMapper.fromInstituteUpdateDTOToInstitute(instituteUpdateDTO);
        institute.setId(id);
        repository.save(institute);

        return instituteMapper.toInstituteDetailsDTO(institute);
    }

}
