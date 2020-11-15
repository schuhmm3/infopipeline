package com.basf.infopipeline.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImportFileService {

  void importFile(MultipartFile zipFile) throws Exception;
  void importXmlFile(MultipartFile xmlFile) throws Exception;

}
