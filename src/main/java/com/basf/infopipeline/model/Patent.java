package com.basf.infopipeline.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

//FIXME: for simplicity we use the same object for model and data , but we should separate to avoid bleeding between parts of the app.
@Document(collection = "Patent")
@Data
public class Patent {

  private ApplicationReference applicationReference;

  private String inventionTitle;

  private String abstractText;

  private String date;

}
