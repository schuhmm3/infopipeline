package com.basf.infopipeline.repository;

import com.basf.infopipeline.model.Patent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatentRepository extends MongoRepository<Patent, String> {


}
