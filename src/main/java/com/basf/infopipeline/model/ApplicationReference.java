package com.basf.infopipeline.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ApplicationReference {

  private List<DocumentId> documents;


}
