package com.basf.infopipeline.service;

import com.basf.infopipeline.repository.PatentDao;

public interface DataService {

  void persist(PatentDao patent);

}
