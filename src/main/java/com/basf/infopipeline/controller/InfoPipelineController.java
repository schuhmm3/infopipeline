package com.basf.infopipeline.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController("/admin")
public class InfoPipelineController {


  @PostMapping("/import")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void importFile(@RequestParam("xmlFile") MultipartFile xmlFile){


  }

  @PostMapping("/mongoflush")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteMongo(){

  }

}
