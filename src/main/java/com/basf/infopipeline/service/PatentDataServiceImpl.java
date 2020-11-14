package com.basf.infopipeline.service;


import com.basf.infopipeline.model.Patent;
import com.basf.infopipeline.repository.PatentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatentDataServiceImpl implements PatentDataService {


  private PatentRepository patentRepository;

  @Autowired
  public PatentDataServiceImpl(PatentRepository patentRepository) {
    this.patentRepository = patentRepository;
  }

  @Override
  public void persist(Patent patent) {
    patentRepository.save(patent);
  }
}
