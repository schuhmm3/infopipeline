package com.basf.infopipeline.service;

import com.basf.infopipeline.model.ApplicationReference;
import com.basf.infopipeline.model.NamedEntity;
import com.basf.infopipeline.repository.PatentDao;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@Service
@Slf4j
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

        log.debug("Parsing file :" + xmlFile.getOriginalFilename());
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

        JAXBContext jaxbContext = JAXBContext.newInstance(ApplicationReference.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        ApplicationReference applicationReference = (ApplicationReference) jaxbUnmarshaller.unmarshal (application);


        PatentDao patent = new PatentDao();
        patent.setAbstractText(abstractText);
        patent.setInventionTitle(title);
        patent.setDate(date);
        patent.setApplicationReference(applicationReference);

        log.debug("Persisting patent file");
        patentDataService.persist(patent);
        log.debug("Parsing named entities from abstract and description");
        NamedEntity namedEntity = nlpService.findNamedEntity(abstractText, description);
        log.debug("Persisting named entities");
        //FIXME: how do we want to persist NE?
        // what use are we gonna have ? keep patent ID ? duplicates ? one entry per NE ?Is it gonna be like a lookuptable or are we gonna run statistics on chemicals
        namedEntityDataService.persist(namedEntity);
        log.debug("End processing for file :" + xmlFile.getOriginalFilename());

      } catch (Exception e) {

      }
    }
  }
}
