package com.basf.infopipeline.model;

import javax.xml.bind.annotation.XmlElement;

public class DocumentId {

  @XmlElement
  private String country;
  @XmlElement(name = "doc-number")
  private String docNumber;
  @XmlElement
  private String kind;
  @XmlElement
  private String date;

//      <document-id>
//        <country>US</country>
//        <doc-number>06189068</doc-number>
//        <kind>B1</kind>
//        <date>20010213</date>
//      </document-id>
//      <document-id data-format="questel">
//        <doc-number>US6189068</doc-number>
//      </document-id>

}
