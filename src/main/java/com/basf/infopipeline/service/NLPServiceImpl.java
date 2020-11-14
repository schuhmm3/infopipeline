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

    if (StringUtils.isNotEmpty(abstractText)) {
      Document abstractDocument = new Document(abstractText);
      namedEntity.getNamedEntities().addAll(getNE(abstractDocument));
    }
    if (StringUtils.isNotEmpty(description)) {
      Document descriptionDocument = new Document(description);
      namedEntity.getNamedEntities().addAll(getNE(descriptionDocument));
    }

    return namedEntity;
  }

  private List<String> getNE(Document doc) {
    //TODO:Chemicals are returned as MISC type, filter by MISC type. Then see bonus question about ChemNER.
    // how to get value of identified NE??? punctuations count as elements.
    //TODO:Maybe there is a better way to get the Value associated to the tag?
    //TODO: this function does not care about duplicates, maybe improve with a count foreach unique NE found in each patent ? depending on business requirements
    List<String> neValues = new ArrayList<>();
    doc.sentences().forEach(
        s -> {
          List<String> nerTags = s.nerTags();
          for (int i = 0; i < nerTags.size(); i++) {
            if ("MISC".equals(nerTags.get(i))) {
              neValues.add(s.originalText(i));
            }
          }
        }
    );
    return neValues;
  }
}
