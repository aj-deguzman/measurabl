package com.msr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.msr.client.BuildingsAPIClient;

@SpringBootApplication
public class BuildingsApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(BuildingsApiApplication.class, args);

		BuildingsAPIClient bac = new BuildingsAPIClient();
		bac.generateTablesAndAddData();
	}
}
