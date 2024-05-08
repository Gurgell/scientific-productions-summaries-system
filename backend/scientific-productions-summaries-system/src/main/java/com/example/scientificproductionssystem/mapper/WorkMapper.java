package com.example.scientificproductionssystem.mapper;

import com.example.scientificproductionssystem.dto.researcher.ResearcherDetailsDTO;
import com.example.scientificproductionssystem.dto.work.WorkDetailsDTO;
import com.example.scientificproductionssystem.model.QuoteName;
import com.example.scientificproductionssystem.model.Researcher;
import com.example.scientificproductionssystem.model.Work;
import com.example.scientificproductionssystem.model.worktypes.Article;
import com.example.scientificproductionssystem.model.worktypes.Book;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WorkMapper {
    @Autowired
    private ModelMapper mapper;

    public WorkDetailsDTO toWorkDetailsDTO(Work work){
        String quotes = "";
        if(!work.getQuoteNames().isEmpty()) {
            QuoteName lastQuote = work.getQuoteNames().getLast();
            for (QuoteName quote : work.getQuoteNames()) {
                quotes += quote.getName();
                if (quote != lastQuote)
                     quotes += "; ";
                else
                    quotes += " . ";
            }
        }

        String details = "";

        if(work.getClass() == Book.class){
            Book book = (Book) work;
            details += book.getChapterTitle() + ". ";
        }

        details += work.getTitle() + ". ";

        if(work.getClass() == Article.class){
            Article article = (Article) work;
            if (article.getPlace() != null)
                details += article.getPlace() + ", ";
        }

        details += work.getYear();
        
        return new WorkDetailsDTO(
                work.getId(),
                work.getClass().getSimpleName(),
                new StringBuilder(quotes + details).toString()
        );
    }

    public List<WorkDetailsDTO> fromListWorksToWorksDetailsDTO(List<Work> works){
        List<WorkDetailsDTO> workDetailsDTOS = new ArrayList<>();
        for (Work work : works) {
            workDetailsDTOS.add(toWorkDetailsDTO(work));
        }
        return workDetailsDTOS;
    }

    public Page<WorkDetailsDTO> fromPageWorksToWorksDetailsDTO(Page<Work> works){
        return works.map(this::toWorkDetailsDTO);
    }

}
