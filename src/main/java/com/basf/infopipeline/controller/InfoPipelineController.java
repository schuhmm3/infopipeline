package com.basf.infopipeline.controller;

import com.basf.infopipeline.service.ImportFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class InfoPipelineController {

  @Autowired
  ImportFileService importFileService;

  @PostMapping("/import")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void importFile(@RequestParam("xmlFile") MultipartFile xmlFile) {
    importFileService.importFile(xmlFile);

  }

  @PostMapping("/mongoflush")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteMongo() {

  }

  @GetMapping("/import")
  public String importFile() {
    return "hello ";
  }


}
