package com.basf.infopipeline.service;

import com.basf.infopipeline.model.ApplicationReference;
import com.basf.infopipeline.model.Patent;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@Service
@Slf4j
public class XmlPatentParserImpl implements XmlPatentParser {

  private static final String XPATH_DATE = "/questel-patent-document/bibliographic-data/publication-reference/document-id/date";
  private static final String XPATH_APPLICATION = "/questel-patent-document/bibliographic-data/application-reference";
  private static final String XPATH_TITLE = "/questel-patent-document/bibliographic-data/invention-title";
  private static final String XPATH_ABSTRACT = "/questel-patent-document/abstract";
  private static final String XPATH_DESCRIPTION = "/questel-patent-document/description";

  @Override
  public Patent getPatent(Document document) {
    Patent patent = new Patent();
    try {
      XPath xPath = XPathFactory.newInstance().newXPath();

      String expression = XPATH_DATE;
      String date = (String) xPath.compile(expression).evaluate(document, XPathConstants.STRING);
      expression = XPATH_ABSTRACT;
      String abstractText = (String) xPath.compile(expression).evaluate(document, XPathConstants.STRING);
      expression = XPATH_TITLE;
      String title = (String) xPath.compile(expression).evaluate(document, XPathConstants.STRING);
      expression = XPATH_DESCRIPTION;
      String description = (String) xPath.compile(expression).evaluate(document, XPathConstants.STRING);
      expression = XPATH_APPLICATION;
      Node application = (Node) xPath.compile(expression).evaluate(document, XPathConstants.NODE);

      JAXBContext jaxbContext = JAXBContext.newInstance(ApplicationReference.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      ApplicationReference applicationReference = (ApplicationReference) jaxbUnmarshaller.unmarshal(application);

      patent.setAbstractText(abstractText);
      patent.setInventionTitle(title);
      patent.setDate(date);
      patent.setApplicationReference(applicationReference);
      patent.setDescription(description);

    } catch (XPathExpressionException | JAXBException e) {
      log.error("Error extracting xml data from document", e.getMessage());
      e.printStackTrace();
    }

    return patent;
  }
}
