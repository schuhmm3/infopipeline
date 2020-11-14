package com.basf.infopipeline.repository;

import com.basf.infopipeline.model.NamedEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NamedEntityRepository extends MongoRepository<NamedEntity, String> {

}
