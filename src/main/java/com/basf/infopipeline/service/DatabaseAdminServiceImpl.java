package com.basf.infopipeline.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class DatabaseAdminServiceImpl implements DatabaseAdminService {


  private MongoTemplate mongoTemplate;


  @Autowired
  public DatabaseAdminServiceImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void dropDatabase() {
    log.debug("dropping database");
    mongoTemplate.getDb().drop();
  }
}
