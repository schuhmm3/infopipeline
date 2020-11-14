package com.basf.infopipeline.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.basf.infopipeline.model.NamedEntity;
import org.junit.jupiter.api.Test;

class NLPServiceImplTest {

  NLPServiceImpl nlpService = new NLPServiceImpl();

  @Test
  void test() {

    NamedEntity namedEntity = nlpService.findNamedEntity(
        "I like Butanol.", null);
    assertThat(namedEntity.getNamedEntities()).isNotNull().hasSize(1).containsExactly("Butanol");

    namedEntity = nlpService.findNamedEntity(
        null, "I like Butanol.");
    assertThat(namedEntity.getNamedEntities()).isNotNull().hasSize(1).containsExactly("Butanol");

    namedEntity = nlpService.findNamedEntity(
        "I like Butanol.", "I like Butanol.");
    assertThat(namedEntity.getNamedEntities()).isNotNull().hasSize(2).containsExactly("Butanol","Butanol");
  }


}
