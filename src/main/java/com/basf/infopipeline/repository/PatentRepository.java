package com.basf.infopipeline.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatentRepository extends MongoRepository<PatentDao, String> {


}
