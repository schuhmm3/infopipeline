package com.basf.infopipeline.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "application-reference")
public class ApplicationReference {

  @XmlElement(name = "document-id")
  private List<DocumentId> documents;


}
