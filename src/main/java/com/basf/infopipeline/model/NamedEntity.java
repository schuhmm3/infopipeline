package com.basf.infopipeline.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Document(collection = "NamedEntity")
//FIXME: for simplicity we use the same object for model and data , but we should separate to avoid bleeding between parts of the app.
public class NamedEntity {

  private List<String> namedEntities;

}
