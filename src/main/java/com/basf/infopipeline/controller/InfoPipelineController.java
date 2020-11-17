package com.basf.infopipeline.controller;

import com.basf.infopipeline.service.DatabaseAdminService;
import com.basf.infopipeline.service.ImportFileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api")
public class InfoPipelineController {

  ImportFileService importFileService;
  DatabaseAdminService databaseAdminService;

  @Autowired
  public InfoPipelineController(ImportFileService importFileService, DatabaseAdminService databaseAdminService) {

    this.importFileService = importFileService;
    this.databaseAdminService = databaseAdminService;
  }

  @ApiOperation(value = "import zip file",notes = "Import zip file containing patents for Ner")
  @PostMapping("/import")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void importFile(@RequestParam("zipFile") MultipartFile zipFile) {
    try {
      importFileService.importFile(zipFile);
    } catch (Exception e) {
      //FIXME: deal with different exceptions and return HTTP code that fits
      //we could distinguish 400 bad request for malformed xml file, then 5XX for internal errors (mongo not available, ...)
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Internal error", e);
    }

  }

  @ApiIgnore
  @PostMapping("/importxml")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void importXmlFile(@RequestParam("xmlFile") MultipartFile xmlFile) {
    try {
      importFileService.importXmlFile(xmlFile);
    } catch (Exception e) {
      //we could distinguish 400 bad request for malformed xml file, then 5XX for internal errors (mongo not available, ...)
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Internal error", e);
    }

  }

  @ApiOperation(value = "drop database",notes = "Deletes the whole database.")
  @PostMapping("/dropdatabase")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void dropDatabase() {
    databaseAdminService.dropDatabase();
  }

}
