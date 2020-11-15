package com.basf.infopipeline.service;

import com.basf.infopipeline.model.NamedEntity;
import com.basf.infopipeline.model.Patent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
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
  public void importFile(MultipartFile zipFile) throws Exception {

    if (!zipFile.isEmpty()) {

      log.debug("Importing zip file :" + zipFile.getOriginalFilename());

      //FIXME: not checking if zipfile contains directories
      try (ZipInputStream zis = new ZipInputStream(zipFile.getInputStream());
      ) {

        ZipEntry entry;
        ByteArrayOutputStream outputStream;
        while ((entry = zis.getNextEntry()) != null) {
          outputStream = new ByteArrayOutputStream();
          byte[] buffer = new byte[1024];
          int len;
          while ((len = zis.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
          }
          log.debug("Parsing file :" + entry.getName());
          importPatent(new ByteArrayInputStream(outputStream.toByteArray()));
          outputStream.close();
        }

      }

      log.debug("End processing for zip file :" + zipFile.getOriginalFilename());

    } else {
      log.error("Provided zip file is empty");
    }
  }

  @Override
  public void importXmlFile(MultipartFile xmlFile) throws Exception {

    importPatent(xmlFile.getInputStream());
  }

  private void importPatent(InputStream xmlInputStream) throws Exception {

    Document doc = getDocument(xmlInputStream);
    Patent patent = xmlPatentParser.getPatent(doc);

    log.debug("Persisting patent file");
    patentDataService.persist(patent);

    log.debug("Parsing named entities from abstract and description");
    NamedEntity namedEntity = nlpService.findNamedEntity(patent.getAbstractText(), patent.getDescription());

    log.debug("Persisting named entities");
    //FIXME: how do we want to persist NE?
    // what use are we gonna have ? keep patent ID ? duplicates ? one entry per NE ?Is it gonna be like a lookuptable or are we gonna run statistics on chemicals
    namedEntityDataService.persist(namedEntity);

  }

  private Document getDocument(InputStream xmlFile) throws Exception {
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(xmlFile);
      doc.getDocumentElement().normalize();
      return doc;
    } catch (Exception e) {
      log.error("Error creating Dom parser for xml file");
      throw e;
    }
  }
}
