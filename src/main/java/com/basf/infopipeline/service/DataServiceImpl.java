package com.basf.infopipeline.service;


import com.basf.infopipeline.repository.PatentDao;
import com.basf.infopipeline.repository.PatentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService {


  private PatentRepository patentRepository;

  @Autowired
  public DataServiceImpl(PatentRepository patentRepository) {
    this.patentRepository = patentRepository;
  }

  @Override
  public void persist(PatentDao patent) {

    patentRepository.save(patent);

  }
}
