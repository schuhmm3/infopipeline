package com.basf.infopipeline.service;

import com.basf.infopipeline.model.NamedEntity;
import com.basf.infopipeline.repository.NamedEntityRepository;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NamedEntityDataServiceImplTest {

  @Mock
  NamedEntityRepository namedEntityRepository;

  @InjectMocks
  NamedEntityDataServiceImpl namedEntityDataService;

  @Test
  void persist() {

    NamedEntity entity = new NamedEntity(Collections.EMPTY_LIST);
    namedEntityDataService.persist(entity);
    Mockito.verify(namedEntityRepository, Mockito.times(1)).save(entity);
  }

}
