package com.basf.infopipeline.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
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

    MockMultipartFile mockMultipartFile = new MockMultipartFile("US0617.zip", getClass().getResourceAsStream(("US0617.zip")));

    importFileService.importFile(mockMultipartFile);

    //FIXME: check various services are called with Mockito.times()

  }


}
