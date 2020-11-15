package com.basf.infopipeline.service;

import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;

class ImportFileServiceImplTest {

  @Mock
  private PatentDataService patentDataService;
  @Mock
  private NamedEntityDataService namedEntityDataService;
  @Mock
  private NLPService nlpService;

  @InjectMocks
  private ImportFileServiceImpl importFileService;

  @Test
  void testImport() throws Exception {

    final InputStream inputStream = getClass().getClassLoader().getResourceAsStream("US06198563B1.xml");
    MockMultipartFile mockMultipartFile = new MockMultipartFile("US06198563B1.xml", inputStream);

    importFileService.importFile(mockMultipartFile);

    //FIXME: check various services are called with Mockito.times()


  }


}
