package com.basf.infopipeline.service;

import com.basf.infopipeline.model.Patent;
import com.basf.infopipeline.repository.PatentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PatentDataServiceImplTest {

  @Mock
  PatentRepository patentRepository;

  @InjectMocks
  PatentDataServiceImpl patentDataService;

  @Test
  void persist() {

    Patent patent = new Patent();
    patentDataService.persist(patent);
    Mockito.verify(patentRepository, Mockito.times(1)).save(patent);
  }
}
