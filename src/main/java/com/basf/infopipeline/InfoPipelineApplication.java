package com.basf.infopipeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class InfoPipelineApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfoPipelineApplication.class, args);
	}

}
