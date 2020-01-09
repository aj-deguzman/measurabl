package com.msr.controller;

import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msr.util.DBManager;

@RestController
@RequestMapping("/dataController")
public class DataController {
	private DBManager dbm;
	private final String SQL_PATH = "/queries/";

	@GetMapping("/siteAndUsageInfo")
	public String getSiteAndUsageInfo(@RequestParam int id) throws ClassNotFoundException, SQLException {
		this.dbm = new DBManager();
		this.dbm.setSqlPath(this.SQL_PATH);
		this.dbm.setSqlFile("SiteAndUsageInfo.sql");
		this.dbm.getQueryFromFile();
		this.dbm.prepareStmt();
		this.dbm.setIntBindVariable(1, id);
		this.dbm.execQuery();

		JSONObject jsonSite = new JSONObject();
		JSONObject jsonType = new JSONObject();

		jsonType.put("id", this.dbm.getRS().getInt("TYPE_ID"));
		jsonType.put("name", this.dbm.getRS().getString("TYPE"));

		jsonSite.put("id", this.dbm.getRS().getInt("SITE_ID"));
		jsonSite.put("name", this.dbm.getRS().getString("SITE_NAME"));
		jsonSite.put("address", this.dbm.getRS().getString("ADDRESS"));
		jsonSite.put("city", this.dbm.getRS().getString("CITY"));
		jsonSite.put("state", this.dbm.getRS().getString("STATE"));
		jsonSite.put("zipcode", this.dbm.getRS().getString("ZIPCODE"));
		jsonSite.put("total_size", this.dbm.getRS().getInt("TOTAL_SIZE"));
		jsonSite.put("primary_type", jsonType);

		return jsonSite.toString();
	}
}
