package com.basf.infopipeline.service;

import com.basf.infopipeline.model.NamedEntity;
import com.basf.infopipeline.repository.PatentDao;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
  private static final String XPATH_ID = "/questel-patent-document/description";


  private final NLPService nlpService;
  private final PatentDataService patentDataService;
  private final NamedEntityDataService namedEntityDataService;

  @Autowired
  public ImportFileServiceImpl(PatentDataService patentDataService, NamedEntityDataService namedEntityDataService,
      NLPService nlpService) {
    this.patentDataService = patentDataService;
    this.nlpService = nlpService;
    this.namedEntityDataService = namedEntityDataService;
  }


  @Override
  public void importFile(MultipartFile xmlFile) {

    if (!xmlFile.isEmpty()) {
      try {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile.getInputStream());
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

        PatentDao patent = new PatentDao();
        patent.setAbstractText(abstractText);
        patent.setInventionTitle(title);
        patent.setDate(date);
        //FIXME:convert Nodes to Java object using Jackson
//        patent.setApplicationReference(applicationReference);

        patentDataService.persist(patent);

        NamedEntity namedEntity = nlpService.findNamedEntity(abstractText, description);
        namedEntityDataService.persist(namedEntity);

        System.out.println(description);

      } catch (Exception e) {

      }
    }
  }
}
