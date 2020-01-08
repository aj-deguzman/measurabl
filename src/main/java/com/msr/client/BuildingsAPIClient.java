package com.msr.client;

import org.springframework.web.client.RestTemplate;

public class BuildingsAPIClient {
	private RestTemplate restTemplate = new RestTemplate();

	public void generateTablesAndAddData() {
		int tblCreate = this.restTemplate.getForObject("http://localhost:8080/buildDatabase/buildTables",
				Integer.class);

		if (tblCreate == 0) {
			int tables = this.restTemplate.getForObject("http://localhost:8080/buildDatabase/populateTables",
					Integer.class);

			if (tables == 0) {
				System.out.println("1 or more tables failed to populate");
			}
		}
	}
}
