package com.basf.infopipeline.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImportFileService {

  void importFile(MultipartFile xmlFile);

}
