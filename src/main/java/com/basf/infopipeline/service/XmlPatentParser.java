package com.basf.infopipeline.service;

import com.basf.infopipeline.model.Patent;
import org.w3c.dom.Document;

public interface XmlPatentParser {

  Patent getPatent(Document document);

}
