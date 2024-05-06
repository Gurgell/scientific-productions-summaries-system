package com.example.scientificproductionssystem.services;

import com.example.scientificproductionssystem.exceptions.ResourceNotFoundException;
import com.example.scientificproductionssystem.exceptions.handler.CustomizedResponseEntityExceptionHandler;
import com.example.scientificproductionssystem.model.QuoteName;
import com.example.scientificproductionssystem.model.Work;
import com.example.scientificproductionssystem.model.worktypes.Article;
import com.example.scientificproductionssystem.model.worktypes.Book;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class XmlSearchService {
    private static final Logger logger = Logger.getLogger(CustomizedResponseEntityExceptionHandler.class.getName());

    public List<Work> findResearcherWorks(Long id){
        try {
            File directory = new File("backend/scientific-productions-summaries-system/src/main/resources/curriculum");
            File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
            if (files != null) {
                for (File file : files) { //Varrendo arquivos XML dentro do diretório especificado acima
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(file);
                    doc.getDocumentElement().normalize();

                    NodeList nodeList = doc.getElementsByTagName("CURRICULO-VITAE");

                    for (int i = 0; i < nodeList.getLength(); i++) { //Iterando para buscar o ID de cada XML. (Ou pelo nome do arquivo ou dentro da TAG "CURRICULO-VITAE")
                        Element element = (Element) nodeList.item(i);
                        Long xmlId = getCurriculumId(file, element);

                        if (xmlId.toString().equals(id.toString())) {
                            Element bibliographicProduction = (Element) element.getElementsByTagName("PRODUCAO-BIBLIOGRAFICA").item(0);

                            List<Work> works = new ArrayList<>();
                            if (bibliographicProduction == null) return works;

                            NodeList productions = bibliographicProduction.getChildNodes();

                            for (int j = 0; j < productions.getLength(); j++) {
                                Node production = productions.item(j);

                                if (production.getNodeType() == Node.ELEMENT_NODE && production.getNodeName().equals("ARTIGOS-PUBLICADOS")) {
                                    NodeList articlesNodeList = production.getChildNodes();

                                    for (int k = 0; k < articlesNodeList.getLength(); k++) { //Iterando sobre cada artigo publicado
                                        Node articleNode = articlesNodeList.item(k);

                                        if (articleNode.getNodeType() == Node.ELEMENT_NODE && articleNode.getNodeName().equals("ARTIGO-PUBLICADO")) {
                                            Element articleElement = (Element) articleNode;
                                            Element articleBasicData = (Element) articleElement.getElementsByTagName("DADOS-BASICOS-DO-ARTIGO").item(0);
                                            Element articleDescriptionData = (Element) articleElement.getElementsByTagName("DETALHAMENTO-DO-ARTIGO").item(0);

                                            String articleTitle = articleBasicData.getAttribute("TITULO-DO-ARTIGO");
                                            Integer year = Integer.parseInt(articleBasicData.getAttribute("ANO-DO-ARTIGO"));
                                            String place = articleDescriptionData.getAttribute("LOCAL-DE-PUBLICACAO");
                                            getQuoteNames(articleElement);

                                            Article article = new Article();
                                            article.setPlace(place);
                                            article.setYear(year);
                                            article.setTitle(articleTitle);
                                            article.setQuoteNames(getQuoteNames(articleElement));

                                            works.add(article);
                                        }
                                    }
                                }else if (production.getNodeType() == Node.ELEMENT_NODE && production.getNodeName().equals("LIVROS-E-CAPITULOS")){
                                    NodeList booksNodeList = production.getChildNodes();

                                    for (int k = 0; k < booksNodeList.getLength(); k++) { //Iterando sobre cada livro publicado
                                        Node bookNode = booksNodeList.item(k);
                                        if (bookNode.getNodeType() == Node.ELEMENT_NODE && bookNode.getNodeName().equals("CAPITULOS-DE-LIVROS-PUBLICADOS")){
                                            NodeList chaptersNodeList = bookNode.getChildNodes();
                                            for (int l = 0; l < chaptersNodeList.getLength(); l++){
                                                Node chapterNode = chaptersNodeList.item(l);
                                                if (chapterNode.getNodeType() == Node.ELEMENT_NODE && chapterNode.getNodeName().equals("CAPITULO-DE-LIVRO-PUBLICADO")){
                                                    Element bookElement = (Element) chapterNode;
                                                    Element bookBasicData = (Element) bookElement.getElementsByTagName("DADOS-BASICOS-DO-CAPITULO").item(0);
                                                    Element bookDescriptionData = (Element) bookElement.getElementsByTagName("DETALHAMENTO-DO-CAPITULO").item(0);

                                                    String bookTitle = bookDescriptionData.getAttribute("TITULO-DO-LIVRO");
                                                    String chapterTitle = bookBasicData.getAttribute("TITULO-DO-CAPITULO-DO-LIVRO");
                                                    Integer year = Integer.parseInt(bookBasicData.getAttribute("ANO"));

                                                    Book book = new Book();
                                                    book.setYear(year);
                                                    book.setTitle(bookTitle);
                                                    book.setChapterTitle(chapterTitle);
                                                    book.setQuoteNames(getQuoteNames(bookElement));

                                                    works.add(book);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            return works;
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

    public String findResearcherName(Long id) {
        try {
            File directory = new File("backend/scientific-productions-summaries-system/src/main/resources/curriculum");
            File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
            if (files != null) {
                for (File file : files) { //Varrendo arquivos XML dentro do diretório especificado acima
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(file);
                    doc.getDocumentElement().normalize();

                    NodeList nodeList = doc.getElementsByTagName("CURRICULO-VITAE");

                    for (int i = 0; i < nodeList.getLength(); i++) { //Iterando para buscar o ID de cada XML. (Ou pelo nome do arquivo ou dentro da TAG "CURRICULO-VITAE")
                        Element element = (Element) nodeList.item(i);
                        Long xmlId = getCurriculumId(file, element);

                        if (xmlId.toString().equals(id.toString())) {
                            Element generalData = (Element) element.getElementsByTagName("DADOS-GERAIS").item(0);

                            return generalData.getAttribute("NOME-COMPLETO");
                        }
                    }
                }
            }
        }catch (IOException | SAXException | ParserConfigurationException e) {
            logger.severe("Error occurred while parsing XML: " + e.getMessage());
            throw new RuntimeException("Error occurred while parsing XML", e);
        }
        throw new ResourceNotFoundException("This curriculum ID doesn't exist!");
    }

    private List<QuoteName> getQuoteNames(Element workElement) throws UnsupportedEncodingException {
        List<QuoteName> quoteNames = new ArrayList<>();

        NodeList authors = workElement.getElementsByTagName("AUTORES");
        for (int m = 0; m < authors.getLength(); m++) {
            Element authorElement = (Element) authors.item(m);
            QuoteName quoteName = new QuoteName();
            String nomeNormalizado = authorElement.getAttribute("NOME-PARA-CITACAO");
            quoteName.setName(nomeNormalizado);
            quoteNames.add(quoteName);
        }

        return quoteNames;
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
