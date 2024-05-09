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
        Page<Work> works;

        Pageable pageable = PageRequest.of(page, limit, Sort.unsorted());

        if (idInstitute.isPresent() && idResearcher.isPresent()) {
           if(type.equals("book"))
                works = bookRepository.findByResearcherIdAndYearBetween(idResearcher.get(), startYear, endYear, pageable);

            else if(type.equals("article"))
                works = articleRepository.findByResearcherIdAndYearBetween(idResearcher.get(), startYear, endYear, pageable);

            else //all
                works = repository.findByResearcherIdAndYearBetween(idResearcher.get(), startYear, endYear, pageable);
        }

//        else if (idInstitute.isPresent()) {
//            if(type.equals("all"))
//
//            else if(type.equals("book"))
//
//            else if(type.equals("article"))
//        }

        else if(idResearcher.isPresent()){
            if(type.equals("book"))
                works = bookRepository.findByResearcherIdAndYearBetween(idResearcher.get(), startYear, endYear, pageable);

            else if(type.equals("article"))
                works = articleRepository.findByResearcherIdAndYearBetween(idResearcher.get(), startYear, endYear, pageable);

            else //all
                works = repository.findByResearcherIdAndYearBetween(idResearcher.get(), startYear, endYear, pageable);
        }

        else {  //idResearcher.isEmpty() && idInstitute.isEmpty()
            if(type.equals("book"))
                works = bookRepository.findByYearBetween(startYear, endYear, pageable);

            else if(type.equals("article"))
                works = articleRepository.findByYearBetween(startYear, endYear, pageable);

            else //all
                works = repository.findByYearBetween(startYear, endYear, pageable);
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

        return new PageImpl<>(uniqueWorkDTOs, pageable, uniqueWorkDTOs.size());
    }

//    public Page<WorkDetailsDTO> findAllByDateOrInstituteOrResearcherOrType(Integer page, Integer limit, Integer startYear, Integer endYear, Long idInstitute, Long idResearcher, String type) {
//        Pageable pageable = PageRequest.of(page, limit, Sort.unsorted());
//
//        Institute institute = null;
//        Researcher researcher = null;
//        if (idInstitute != null) {
//            institute = instituteRepository.findById(idInstitute).orElse(null);
//        }
//
//        if (idResearcher != null) {
//            researcher = researcherRepository.findById(idResearcher).orElse(null);
//        }
//
//        //Recuperando a lista de trabalhos de acordo com os critérios passados pelo usuário
//        Page<Work> works = repository.findAllByDateOrInstituteOrResearcherOrType(institute, researcher, startYear, endYear, type, pageable);
//        if (works.isEmpty()) throw new ResourceNotFoundException("No works found!");
//
//        //Validando se o trabalho é único
//        List<WorkDetailsDTO> uniqueWorkDTOs = new ArrayList<>();
//        Set<String> uniqueWorksByTitleAndYearAndChapterTitleAndPlace = new HashSet<>();
//
//        for (Work work : works) {
//            String chapterTitle = "";
//            String place = "";
//            if (work instanceof Book) chapterTitle = ((Book) work).getChapterTitle();
//            else if (work instanceof Article) place = ((Article) work).getPlace();
//
//            //Analisar esse parte do código para adicionar ao equals & hashcode da classe
//            String workHash = work.getTitle() + "-" + work.getYear() + "-" + chapterTitle + "-" + place;
//            if (!uniqueWorksByTitleAndYearAndChapterTitleAndPlace.contains(workHash)) {
//                uniqueWorksByTitleAndYearAndChapterTitleAndPlace.add(workHash);
//                uniqueWorkDTOs.add(workMapper.toWorkDetailsDTO(work));
//            }
//        }
//
//        return new PageImpl<>(uniqueWorkDTOs, pageable, uniqueWorkDTOs.size());
//    }
}
