package com.example.scientificproductionssystem.mapper;

import com.example.scientificproductionssystem.dto.work.WorkDetailsDTO;
import com.example.scientificproductionssystem.model.QuoteName;
import com.example.scientificproductionssystem.model.Work;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        
        return new WorkDetailsDTO(
                work.getId(),
                work.getClass().getSimpleName(),
                new StringBuilder(quotes + work.getTitle() + ", " + work.getYear()).toString()
        );
    }

    public List<WorkDetailsDTO> fromListWorksToWorksDetailsDTO(List<Work> works){
        List<WorkDetailsDTO> workDetailsDTOS = new ArrayList<>();
        for (Work work : works) {
            workDetailsDTOS.add(toWorkDetailsDTO(work));
        }
        return workDetailsDTOS;
    }

}
