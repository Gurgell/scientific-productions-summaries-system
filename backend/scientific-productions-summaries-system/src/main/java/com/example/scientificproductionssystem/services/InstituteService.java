package com.example.scientificproductionssystem.services;

import com.example.scientificproductionssystem.exceptions.RequiredObjectIsNullException;
import com.example.scientificproductionssystem.exceptions.ResourceNotFoundException;
import com.example.scientificproductionssystem.model.Institute;
import com.example.scientificproductionssystem.repositories.InstituteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InstituteService {

    @Autowired
    InstituteRepository repository;

    public Institute findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    public Institute create(Institute institute) {
        if (institute == null) throw new RequiredObjectIsNullException();

        return repository.save(institute);
    }

    public void delete(Long id){

        Institute entity = this.findById(id);

        repository.delete(entity);

    }

    public List<Institute> findAll() {
        return repository.findAll();
    }

    public Institute update(Institute institute) {
        Institute entity = this.findById(institute.getId());

        return repository.save(institute);
    }
}
