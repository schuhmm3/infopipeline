package com.basf.infopipeline.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Patent {

  private String date;
  //metadata
  private ApplicationReference applicationReference;
  @JsonProperty("invention-title")
  private String inventionTitle;

  //abstract is Html like
  private String  abstractText;
//    <abstract format="original" lang="en" id="abstr_en">
//    <p id="P-EN-00001" num="00001">
//      <br/>
//  A superscalar microprocessor employing a data cache configured to perform store accesses in a single clock cycle is provided.
//      <br/>
//  The superscalar microprocessor speculatively stores data within a predicted way of the data cache after capturing the data currently being stored in that predicted way.
//      <br/>
//  During a subsequent clock cycle, the cache hit information for the store access validates the way prediction.
//      <br/>
//  If the way prediction is correct, then the store is complete, utilizing a single clock cycle of data cache bandwidth.
//      <br/>
//  Additionally, the way prediction structure implemented within the data cache bypasses the tag comparisons of the data cache to select data bytes for the output.
//      <br/>
//  Therefore, the access time of the associative data cache may be substantially similar to a direct-mapped cache access time.
//      <br/>
//  The superscalar microprocessor may therefore be capable of high frequency operation.
//    </p>
//

  //description is Html
  private String description;

}
