package com.basf.infopipeline.service;

import com.basf.infopipeline.model.Patent;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImportFileServiceImpl implements ImportFileService {


  @Override
  public void importFile(MultipartFile xmlFile) {

    if (!xmlFile.isEmpty()) {
      try {
        byte[] bytes = xmlFile.getBytes();
        XmlMapper xmlMapper = new XmlMapper();
        Patent patent = xmlMapper.readValue(bytes, Patent.class);


//        return "You successfully uploaded " + name + "!";
      } catch (Exception e) {
//        return "You failed to upload " + name + " => " + e.getMessage();
      }
    } else {
//      return "You failed to upload " + name + " because the file was empty.";
    }

  }
}
