//package com.basf.infopipeline.controller;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.basf.infopipeline.MongoConfig;
//import com.basf.infopipeline.service.DatabaseAdminService;
//import com.basf.infopipeline.service.ImportFileService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//@WebMvcTest(value = InfoPipelineController.class, excludeAutoConfiguration = MongoConfig.class)
//class InfoPipelineControllerTest {
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  @MockBean
//  private ImportFileService importFileService;
//
//  @MockBean
//  private DatabaseAdminService databaseAdminService;
//
//
//  @Test
//  void importFile() throws Exception {
//    this.mockMvc.perform(post("/import").param("zipFile", "")).andExpect(status().isNoContent());
//
//  }
//
//  @Test
//  void importXmlFile() {
//  }
//
//  @Test
//  void dropDatabase() throws Exception {
////    doNothing().when(databaseAdminService).dropDatabase();
////    this.mockMvc.perform(post("/db/drop")).andExpect(status().isNoContent());
////    verify(databaseAdminService, Mockito.times(1)).dropDatabase();
//  }
//}
