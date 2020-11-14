package com.basf.infopipeline.service;

import com.basf.infopipeline.model.Patent;

public interface PatentDataService {

  void persist(Patent patent);

}
