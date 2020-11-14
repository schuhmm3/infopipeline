package com.basf.infopipeline.service;

import com.basf.infopipeline.model.NamedEntity;
import com.basf.infopipeline.repository.NamedEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NamedEntityDataServiceImpl implements NamedEntityDataService {

  private NamedEntityRepository namedEntityRepository;


  @Autowired
  public NamedEntityDataServiceImpl(NamedEntityRepository namedEntityRepository) {
    this.namedEntityRepository = namedEntityRepository;
  }


  @Override
  public void persist(NamedEntity namedEntity) {
    namedEntityRepository.save(namedEntity);
  }

}
