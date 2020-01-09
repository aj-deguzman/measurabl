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

    @GetMapping("/siteAndUsageInfo")
    public String getSiteAndUsageInfo(@RequestParam int id) throws ClassNotFoundException, SQLException {
	this.dbm = new DBManager();
	this.dbm.setSqlPath(this.SQL_PATH);
	this.dbm.setSqlFile("SiteAndUsageInfo.sql");
	this.dbm.getQueryFromFile();
	this.dbm.prepareStmt();
	this.dbm.setIntBindVariable(1, id);
	this.dbm.execQuery();

	if (!this.getSaUJO().isEmpty()) {
	    return this.getSaUJO().toString();
	} else {
	    return "No data to return";
	}
    }

    @GetMapping("/allSiteInfo")
    public String getAllSiteInfo() throws ClassNotFoundException, SQLException {
	this.dbm = new DBManager();
	this.dbm.setSqlPath(this.SQL_PATH);
	this.dbm.setSqlFile("AllSites.sql");
	this.dbm.getQueryFromFile();
	this.dbm.prepareStmt();
	this.dbm.execQuery();

	if (!this.getASJO().isEmpty()) {
	    return this.getASJO().toString();
	} else {
	    return "No data to return";
	}
    }

    @GetMapping("/sdSiteInfo")
    public String getSDSiteInfo() throws ClassNotFoundException, SQLException {
	this.dbm = new DBManager();
	this.dbm.setSqlPath(this.SQL_PATH);
	this.dbm.setSqlFile("SanDiegoSiteInfo.sql");
	this.dbm.getQueryFromFile();
	this.dbm.prepareStmt();
	this.dbm.execQuery();

	if (!this.getSDSJO().isEmpty()) {
	    return this.getSDSJO().toString();
	} else {
	    return "No data to return";
	}
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

    private JSONObject getSaUJO() throws SQLException {
	JSONObject jsonSite = new JSONObject();
	JSONObject jsonType = new JSONObject();

	if (this.dbm.getRS().next()) {
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
	}

	return jsonSite;
    }

    private JSONArray getASJO() throws SQLException {
	JSONArray siteArray = new JSONArray();

	while (this.dbm.getRS().next()) {
	    JSONObject jsonObj = new JSONObject();

	    jsonObj.put("id", this.dbm.getRS().getInt("ID"));
	    jsonObj.put("name", this.dbm.getRS().getString("NAME"));
	    jsonObj.put("address", this.dbm.getRS().getString("ADDRESS"));
	    jsonObj.put("city", this.dbm.getRS().getString("CITY"));
	    jsonObj.put("state", this.dbm.getRS().getString("STATE"));
	    jsonObj.put("zipcode", this.dbm.getRS().getString("ZIPCODE"));

	    siteArray.add(jsonObj);
	}

	return siteArray;
    }

    private JSONObject getSDSJO() throws SQLException {
	JSONObject jsonSite = new JSONObject();

	if (this.dbm.getRS().next()) {
	    jsonSite.put("site_id", this.dbm.getRS().getInt("SITE_ID"));
	    jsonSite.put("site_name", this.dbm.getRS().getString("NAME"));
	    jsonSite.put("address", this.dbm.getRS().getString("ADDRESS"));
	    jsonSite.put("city", this.dbm.getRS().getString("CITY"));
	    jsonSite.put("state", this.dbm.getRS().getString("STATE"));
	    jsonSite.put("zipcode", this.dbm.getRS().getString("ZIPCODE"));
	    jsonSite.put("description", this.dbm.getRS().getString("DESCRIPTION"));
	    jsonSite.put("type", this.dbm.getRS().getString("TYPE"));
	}

	return jsonSite;
    }
}
