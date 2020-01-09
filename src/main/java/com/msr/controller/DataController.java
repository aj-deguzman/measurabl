package com.msr.controller;

import java.sql.SQLException;

import org.json.simple.JSONArray;
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
	private JSONArray siteArray;
	private JSONObject jsonSite;
	private JSONObject jsonType;

	@GetMapping("/siteAndUsageInfo")
	public JSONArray getSiteAndUsageInfo(@RequestParam int id) throws ClassNotFoundException, SQLException {
		this.dbm = new DBManager();
		this.dbm.setSqlPath(this.SQL_PATH);
		this.dbm.setSqlFile("SiteAndUsageInfo.sql");
		this.dbm.getQueryFromFile();
		this.dbm.prepareStmt();
		this.dbm.setIntBindVariable(1, id);
		this.dbm.execQuery();

		return this.getSaUJA();
	}

	@GetMapping("/allSiteInfo")
	public JSONArray getAllSiteInfo() throws ClassNotFoundException, SQLException {
		this.dbm = new DBManager();
		this.dbm.setSqlPath(this.SQL_PATH);
		this.dbm.setSqlFile("AllSites.sql");
		this.dbm.getQueryFromFile();
		this.dbm.prepareStmt();
		this.dbm.execQuery();

		return this.getASJA();
	}

	@GetMapping("/sdSiteInfo")
	public JSONArray getSDSiteInfo() throws ClassNotFoundException, SQLException {
		this.dbm = new DBManager();
		this.dbm.setSqlPath(this.SQL_PATH);
		this.dbm.setSqlFile("SanDiegoSiteInfo.sql");
		this.dbm.getQueryFromFile();
		this.dbm.prepareStmt();
		this.dbm.execQuery();

		return this.getSDSJA();
	}

	@GetMapping("/deleteSiteByCity")
	public String deleteSiteByCity(@RequestParam String city) throws ClassNotFoundException, SQLException {
		this.dbm = new DBManager();
		this.dbm.setSqlPath(this.SQL_PATH);
		this.dbm.setSqlFile("DeleteSiteByCity.sql");
		this.dbm.getQueryFromFile();
		this.dbm.prepareStmt();
		this.dbm.setStringBindVariable(1, city);
		this.dbm.execUpdate();

		if (this.dbm.getUpdateResult() > 0) {
			return "All records in the city of " + city + " has been succesfully deleted from Sites";
		} else {
			return "No data to delete for " + city;
		}
	}

	@SuppressWarnings("unchecked")
	private JSONArray getSaUJA() throws SQLException {
		this.siteArray = new JSONArray();

		while (this.dbm.getRS().next()) {
			this.jsonSite = new JSONObject();
			this.jsonType = new JSONObject();

			this.jsonType.put("id", this.dbm.getRS().getInt("TYPE_ID"));
			this.jsonType.put("name", this.dbm.getRS().getString("TYPE"));

			this.jsonSite.put("id", this.dbm.getRS().getInt("SITE_ID"));
			this.jsonSite.put("name", this.dbm.getRS().getString("SITE_NAME"));
			this.jsonSite.put("address", this.dbm.getRS().getString("ADDRESS"));
			this.jsonSite.put("city", this.dbm.getRS().getString("CITY"));
			this.jsonSite.put("state", this.dbm.getRS().getString("STATE"));
			this.jsonSite.put("zipcode", this.dbm.getRS().getString("ZIPCODE"));
			this.jsonSite.put("total_size", this.dbm.getRS().getInt("TOTAL_SIZE"));
			this.jsonSite.put("primary_type", this.jsonType);

			this.siteArray.add(this.jsonSite);
		}

		if (!this.siteArray.isEmpty()) {
			return this.siteArray;
		} else {
			this.jsonSite = new JSONObject();
			this.jsonSite.put("response", "No data to return");
			this.siteArray.add(this.jsonSite);

			return this.siteArray;
		}
	}

	@SuppressWarnings("unchecked")
	private JSONArray getASJA() throws SQLException {
		this.siteArray = new JSONArray();

		while (this.dbm.getRS().next()) {
			this.jsonSite = new JSONObject();

			this.jsonSite.put("id", this.dbm.getRS().getInt("ID"));
			this.jsonSite.put("name", this.dbm.getRS().getString("NAME"));
			this.jsonSite.put("address", this.dbm.getRS().getString("ADDRESS"));
			this.jsonSite.put("city", this.dbm.getRS().getString("CITY"));
			this.jsonSite.put("state", this.dbm.getRS().getString("STATE"));
			this.jsonSite.put("zipcode", this.dbm.getRS().getString("ZIPCODE"));

			siteArray.add(this.jsonSite);
		}

		if (!this.siteArray.isEmpty()) {
			return this.siteArray;
		} else {
			this.jsonSite = new JSONObject();
			this.jsonSite.put("response", "No data to return");
			this.siteArray.add(this.jsonSite);

			return this.siteArray;
		}
	}

	@SuppressWarnings("unchecked")
	private JSONArray getSDSJA() throws SQLException {
		this.siteArray = new JSONArray();

		while (this.dbm.getRS().next()) {
			this.jsonSite = new JSONObject();

			this.jsonSite.put("site_id", this.dbm.getRS().getInt("SITE_ID"));
			this.jsonSite.put("site_name", this.dbm.getRS().getString("NAME"));
			this.jsonSite.put("address", this.dbm.getRS().getString("ADDRESS"));
			this.jsonSite.put("city", this.dbm.getRS().getString("CITY"));
			this.jsonSite.put("state", this.dbm.getRS().getString("STATE"));
			this.jsonSite.put("zipcode", this.dbm.getRS().getString("ZIPCODE"));
			this.jsonSite.put("description", this.dbm.getRS().getString("DESCRIPTION"));
			this.jsonSite.put("type", this.dbm.getRS().getString("TYPE"));
			this.siteArray.add(this.jsonSite);
		}

		if (!this.siteArray.isEmpty()) {
			return this.siteArray;
		} else {
			this.jsonSite = new JSONObject();
			this.jsonSite.put("response", "No data to return");
			this.siteArray.add(this.jsonSite);

			return this.siteArray;
		}
	}
}
