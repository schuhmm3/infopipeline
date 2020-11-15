package com.basf.infopipeline.service;

import com.basf.infopipeline.model.NamedEntity;
import com.basf.infopipeline.model.Patent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

@Service
@Slf4j
public class ImportFileServiceImpl implements ImportFileService {

  private final NLPService nlpService;
  private final XmlPatentParser xmlPatentParser;
  private final PatentDataService patentDataService;
  private final NamedEntityDataService namedEntityDataService;

  @Autowired
  public ImportFileServiceImpl(XmlPatentParser xmlPatentParser, PatentDataService patentDataService,
      NamedEntityDataService namedEntityDataService,
      NLPService nlpService) {
    this.xmlPatentParser = xmlPatentParser;
    this.patentDataService = patentDataService;
    this.nlpService = nlpService;
    this.namedEntityDataService = namedEntityDataService;
  }


  @Override
  public void importFile(MultipartFile xmlFile) throws Exception {

    if (!xmlFile.isEmpty()) {

      log.debug("Parsing file :" + xmlFile.getOriginalFilename());

      Document doc = getDocument(xmlFile);
      Patent patent = xmlPatentParser.getPatent(doc);

      log.debug("Persisting patent file");
      patentDataService.persist(patent);

      log.debug("Parsing named entities from abstract and description");
      NamedEntity namedEntity = nlpService.findNamedEntity(patent.getAbstractText(), patent.getDescription());

      log.debug("Persisting named entities");
      //FIXME: how do we want to persist NE?
      // what use are we gonna have ? keep patent ID ? duplicates ? one entry per NE ?Is it gonna be like a lookuptable or are we gonna run statistics on chemicals
      namedEntityDataService.persist(namedEntity);

      log.debug("End processing for file :" + xmlFile.getOriginalFilename());

    } else {
      log.error("Provided xml file is empty");
    }
  }

  private Document getDocument(MultipartFile xmlFile) throws Exception {
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(xmlFile.getInputStream());
      doc.getDocumentElement().normalize();
      return doc;
    } catch (Exception e) {
      log.error("Error creating Dom parser for xml file");
      throw e;
    }
  }
}
