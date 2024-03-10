package com.example.scientificproductionssystem.services;

import com.example.scientificproductionssystem.exceptions.RequiredObjectIsNullException;
import com.example.scientificproductionssystem.exceptions.ResourceNotFoundException;
import com.example.scientificproductionssystem.model.Institute;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.repositories.InstituteRepository;
import com.example.scientificproductionssystem.repositories.ResearcherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResearcherService {

    @Autowired
    ResearcherRepository repository;

    @Autowired
    InstituteRepository instituteRepository;

    public Researcher findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    public Researcher create(Researcher researcher, Long id_institute) {
        Institute institute = instituteRepository.findById(id_institute).orElseThrow(() -> new RequiredObjectIsNullException("No records found for this institute ID!"));
        if (researcher == null) throw new RequiredObjectIsNullException();

        researcher.setInstitute(institute);
        return repository.save(researcher);
    }

    public void delete(Long id){

        Researcher researcher = this.findById(id);

        repository.delete(researcher);

    }

    public List<Researcher> findAll() {
        return repository.findAll();
    }

    public Researcher update(Researcher researcher) {
        Researcher entity = this.findById(researcher.getId());

        return repository.save(researcher);
    }
}
