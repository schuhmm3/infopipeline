package com.basf.infopipeline.repository;

import com.basf.infopipeline.model.ApplicationReference;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Patent")
@Data
public class PatentDao {

  private ApplicationReference applicationReference;

  private String inventionTitle;

  private String abstractText;

  private String date;

}
