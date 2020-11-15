package com.basf.infopipeline.controller;

import com.basf.infopipeline.service.DatabaseAdminService;
import com.basf.infopipeline.service.ImportFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class InfoPipelineController {

  ImportFileService importFileService;
  DatabaseAdminService databaseAdminService;

  @Autowired
  public InfoPipelineController(ImportFileService importFileService, DatabaseAdminService databaseAdminService) {

    this.importFileService = importFileService;
    this.databaseAdminService = databaseAdminService;
  }


  @PostMapping("/import")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void importFile(@RequestParam("xmlFile") MultipartFile xmlFile) {
    try {
      importFileService.importFile(xmlFile);
    } catch (Exception e) {
      //FIXME: deal with different exceptions and return HTTP code that fits
      //we could distinguish 400 bad request for malformed xml file, then 5XX for internal errors (mongo not available, ...)
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Internal error", e);
    }

  }

  @PostMapping("/db/drop")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void dropDatabase() {
    databaseAdminService.dropDatabase();
  }

}
