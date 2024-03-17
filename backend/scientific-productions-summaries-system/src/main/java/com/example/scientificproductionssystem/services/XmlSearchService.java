package com.example.scientificproductionssystem.services;

import com.example.scientificproductionssystem.exceptions.ResourceNotFoundException;
import com.example.scientificproductionssystem.exceptions.handler.CustomizedResponseEntityExceptionHandler;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

@Service
public class XmlSearchService {
    private static final Logger logger = Logger.getLogger(CustomizedResponseEntityExceptionHandler.class.getName());

    public String findResearcherCurriculum(Long id){
        try {
            File directory = new File("backend/scientific-productions-summaries-system/src/main/resources/curriculum");
            File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
            if (files != null) {
                for (File file : files) {
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(file);
                    doc.getDocumentElement().normalize();

                    NodeList nodeList = doc.getElementsByTagName("CURRICULO-VITAE");

                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Element element = (Element) nodeList.item(i);
                        Long xmlId = getCurriculumId(file, element);

                        if (xmlId.toString().equals(id.toString())) {
                            Element dadosGerais = (Element) element.getElementsByTagName("DADOS-GERAIS").item(0);
                            return dadosGerais.getAttribute("NOME-COMPLETO");
                        }
                    }
                }
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            logger.severe("Error occurred while parsing XML: " + e.getMessage());
            throw new RuntimeException("Error occurred while parsing XML", e);
        }
        throw new ResourceNotFoundException("This curriculum ID doesn't exist!");
    }

    private static Long getCurriculumId(File file, Element element) {
        long xmlId;

        if (element.hasAttribute("NUMERO-IDENTIFICADOR") && !element.getAttribute("NUMERO-IDENTIFICADOR").isBlank() && !element.getAttribute("NUMERO-IDENTIFICADOR").isEmpty()){
            String idAttribute = element.getAttribute("NUMERO-IDENTIFICADOR");
            xmlId = Long.parseLong(idAttribute);
        }else{
            xmlId = Long.parseLong(file.getName().replace(".xml", ""));
        }
        return xmlId;
    }
}
