package com.basf.infopipeline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


@Service
public class DatabaseAdminServiceImpl implements DatabaseAdminService {


  private MongoTemplate mongoTemplate;


  @Autowired
  public DatabaseAdminServiceImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void dropDatabase() {
    mongoTemplate.getDb().drop();
  }
}
