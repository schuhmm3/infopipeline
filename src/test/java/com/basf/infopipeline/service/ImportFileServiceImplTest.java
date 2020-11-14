package com.basf.infopipeline.service;

import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class ImportFileServiceImplTest {

  private ImportFileServiceImpl importFileService = new ImportFileServiceImpl();

  @Test
  void testImport() throws IOException {

    final InputStream inputStream = getClass().getClassLoader().getResourceAsStream("US06198563B1.xml");
    MockMultipartFile mockMultipartFile = new MockMultipartFile("US06198563B1.xml", inputStream);

    importFileService.importFile(mockMultipartFile);


  }


}
