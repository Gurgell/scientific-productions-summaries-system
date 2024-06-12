package com.example.scientificproductionssystem.services;

import com.example.scientificproductionssystem.dto.researcher.ResearcherDetailsDTO;
import com.example.scientificproductionssystem.dto.work.WorkDetailsDTO;
import com.example.scientificproductionssystem.exceptions.ResourceNotFoundException;
import com.example.scientificproductionssystem.mapper.WorkMapper;
import com.example.scientificproductionssystem.model.Institute;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.model.Work;
import com.example.scientificproductionssystem.model.worktypes.Article;
import com.example.scientificproductionssystem.model.worktypes.Book;
import com.example.scientificproductionssystem.repositories.*;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkService {
    @Autowired
    WorkRepository repository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    InstituteRepository instituteRepository;

    @Autowired
    ResearcherRepository researcherRepository;

    @Autowired
    WorkMapper workMapper;

    public WorkDetailsDTO findById(Long id){
        Work work = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        return workMapper.toWorkDetailsDTO(work);
    }

    public List<WorkDetailsDTO> findAll() {
        List<Work> works = repository.findAll();
        if (works.isEmpty()) throw new ResourceNotFoundException("No researchers found!");

        return workMapper.fromListWorksToWorksDetailsDTO(works);
    }

    public Page<WorkDetailsDTO> findWithParams(Integer page, Integer limit, Integer startYear, Integer endYear, Optional<Long> idInstitute, Optional<Long> idResearcher, String type) {
//        Page<Work> works;
        List<Work> works;

        Pageable pageable = PageRequest.of(page, limit, Sort.unsorted());

        if (idInstitute.isPresent() && idResearcher.isPresent()) {
           if(type.equals("book"))
               works = bookRepository.findByResearcherIdAndResearcherInstituteIdAndYearBetween(idResearcher.get(), idInstitute.get(), startYear, endYear);

            else if(type.equals("article"))
                works = articleRepository.findByResearcherIdAndResearcherInstituteIdAndYearBetween(idResearcher.get(), idInstitute.get(), startYear, endYear);

            else
                works = repository.findByResearcherIdAndResearcherInstituteIdAndYearBetween(idResearcher.get(), idInstitute.get(), startYear, endYear);
        }

        else if(idResearcher.isPresent()){
            if(type.equals("book"))
                works = bookRepository.findByResearcherIdAndYearBetween(idResearcher.get(), startYear, endYear);

            else if(type.equals("article"))
                works = articleRepository.findByResearcherIdAndYearBetween(idResearcher.get(), startYear, endYear);

            else
                works = repository.findByResearcherIdAndYearBetween(idResearcher.get(), startYear, endYear);
        }

        else if(idInstitute.isPresent()){
            if(type.equals("book"))
                works = bookRepository.findByResearcherInstituteIdAndYearBetween(idInstitute.get(), startYear, endYear);

            else if(type.equals("article"))
                works = articleRepository.findByResearcherInstituteIdAndYearBetween(idInstitute.get(), startYear, endYear);

            else
                works = repository.findByResearcherInstituteIdAndYearBetween(idInstitute.get(), startYear, endYear);
        }

        else{
            if(type.equals("book"))
                works = bookRepository.findByYearBetween(startYear, endYear);

            else if(type.equals("article"))
                works = articleRepository.findByYearBetween(startYear, endYear);

            else
                works = repository.findByYearBetween(startYear, endYear);
        }



        //Validando se existem trabalhos repetidos
        List<WorkDetailsDTO> uniqueWorkDTOs = new ArrayList<>();
        Set<String> uniqueWorksByTitleAndYearAndChapterTitleAndPlace = new HashSet<>();

        for (Work work : works) {
            String chapterTitle = "";
            String place = "";
            if (work instanceof Book) chapterTitle = ((Book) work).getChapterTitle();
            else if (work instanceof Article) place = ((Article) work).getPlace();

            //Analisar esse parte do código para adicionar ao equals & hashcode da classe
            String workHash = work.getTitle() + "-" + work.getYear() + "-" + chapterTitle + "-" + place;
            if (!uniqueWorksByTitleAndYearAndChapterTitleAndPlace.contains(workHash)) {
                uniqueWorksByTitleAndYearAndChapterTitleAndPlace.add(workHash);
                uniqueWorkDTOs.add(workMapper.toWorkDetailsDTO(work));
            }
        }

        if (uniqueWorkDTOs.isEmpty()) throw new ResourceNotFoundException("No works found!");

        //Capturando os indexes de início e fim da página requerida, para criação manual do resultado.
        int start = (int) pageable.getOffset(); //Offset seria o index inicial baseado na página atual e no tamanho de cada página
        int end = Math.min((start + pageable.getPageSize()), uniqueWorkDTOs.size()); //Defesa para o caso do index final da página ser maior que o tamanho da lista

        return new PageImpl<>(uniqueWorkDTOs.subList(start, end), pageable, uniqueWorkDTOs.size());
    }


}
