package com.basf.infopipeline.service;

import com.basf.infopipeline.model.NamedEntity;
import edu.stanford.nlp.simple.Document;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class NLPServiceImpl implements NLPService {

  @Override
  public NamedEntity findNamedEntity(String abstractText, String description) {

    NamedEntity namedEntity = new NamedEntity(new ArrayList<>());

    if(StringUtils.isNotEmpty(abstractText)) {
      Document abstractDocument = new Document(abstractText);
      namedEntity.getNamedEntities().addAll(getNE(abstractDocument));
    }
    if(StringUtils.isNotEmpty(description)) {
      Document descriptionDocument = new Document(description);
      namedEntity.getNamedEntities().addAll(getNE(descriptionDocument));
    }

    return namedEntity;
  }

  private List<String> getNE(Document doc) {
    List<String> nes = new ArrayList<>();
    doc.sentences().forEach(s -> nes.addAll(s.nerTags()));
    return nes;
  }
}
