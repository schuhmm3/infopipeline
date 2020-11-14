package com.basf.infopipeline.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentId {

  private String country;
  @JsonProperty("doc-number")
  private String docNumber;
  private String kind;
  private String date;

//    <publication-reference publ-desc="Granted patent as first publication">
//      <document-id>
//        <country>US</country>
//        <doc-number>06189068</doc-number>
//        <kind>B1</kind>
//        <date>20010213</date>
//      </document-id>
//      <document-id data-format="questel">
//        <doc-number>US6189068</doc-number>
//      </document-id>
//    </publication-reference>

}
