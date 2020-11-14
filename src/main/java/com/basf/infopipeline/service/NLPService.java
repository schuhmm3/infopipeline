package com.basf.infopipeline.service;

import com.basf.infopipeline.model.NamedEntity;

public interface NLPService {


   NamedEntity findNamedEntity(String abstractText, String description);

}
