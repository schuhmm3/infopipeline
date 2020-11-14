package com.basf.infopipeline.service;

import com.basf.infopipeline.repository.PatentDao;

public interface PatentDataService {

  void persist(PatentDao patent);

}
