package com.basf.infopipeline.service;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@Service
public class ImportFileServiceImpl implements ImportFileService {


  //TODO: extract to constant class, or parameterized properties
  private static final String XPATH_DATE = "/questel-patent-document/bibliographic-data/publication-reference/document-id/date";
  private static final String XPATH_APPLICATION = "/questel-patent-document/bibliographic-data/application-reference";
  private static final String XPATH_TITLE = "/questel-patent-document/bibliographic-data/invention-title";
  private static final String XPATH_ABSTRACT = "/questel-patent-document/abstract";
  private static final String XPATH_DESCRIPTION = "/questel-patent-document/description";


  @Override
  public void importFile(MultipartFile xmlFile) {

    if (!xmlFile.isEmpty()) {
      try {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile.getInputStream());

        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        XPath xPath = XPathFactory.newInstance().newXPath();

        String expression = XPATH_DATE;
        String date = (String) xPath.compile(expression).evaluate(doc, XPathConstants.STRING);
        expression = XPATH_ABSTRACT;
        String abstractText = (String) xPath.compile(expression).evaluate(doc, XPathConstants.STRING);
        expression = XPATH_TITLE;
        String title = (String) xPath.compile(expression).evaluate(doc, XPathConstants.STRING);
        expression = XPATH_DESCRIPTION;
        String description = (String) xPath.compile(expression).evaluate(doc, XPathConstants.STRING);
        expression = XPATH_APPLICATION;
        Node application = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);

        System.out.println(description);

      } catch (Exception e) {

      }
    }
  }
}
