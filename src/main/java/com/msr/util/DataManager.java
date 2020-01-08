package com.msr.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.ResourceUtils;

public class DataManager {
	private DBManager dbm;
	private final String JSON_PATH = "classpath:data/";
	private final String SITE_USES = "site_uses.json";
	private final String SITE = "sites.json";
	private final String USE_TYPES = "use_types.json";

	public DataManager(DBManager dbm) { this.dbm = dbm; }

	public int populateSiteTable() throws ParseException, IOException, SQLException {
		this.dbm.setQuery("INSERT INTO SITES VALUES (?, ?, ?, ?, ?, ?)");
		this.dbm.prepareStmt();

		for (Object o : this.getSitesJSONArray()) {
			JSONObject sites = (JSONObject) o;

			this.dbm.setIntBindVariable(1, Math.toIntExact((long) sites.get("id")));
			this.dbm.setStringBindVariable(2, (String) sites.get("name"));
			this.dbm.setStringBindVariable(3, (String) sites.get("address"));
			this.dbm.setStringBindVariable(4, (String) sites.get("city"));
			this.dbm.setStringBindVariable(5, (String) sites.get("state"));
			this.dbm.setStringBindVariable(6, (String) sites.get("zipcode"));
			this.dbm.execUpdate();
		}

		return this.dbm.getUpdateResult();
	}

	public int populateSiteUsesTable() throws ParseException, IOException, SQLException {
		this.dbm.setQuery("INSERT INTO SITE_USES VALUES (?, ?, ?, ?, ?)");
		this.dbm.prepareStmt();

		for (Object o : this.getSiteUsesJSONArray()) {
			JSONObject uses = (JSONObject) o;

			this.dbm.setIntBindVariable(1, Math.toIntExact((long) uses.get("id")));
			this.dbm.setStringBindVariable(2, (String) uses.get("description"));
			this.dbm.setIntBindVariable(3, Math.toIntExact((long) uses.get("site_id")));
			this.dbm.setIntBindVariable(4, Math.toIntExact((long) uses.get("size_sqft")));
			this.dbm.setIntBindVariable(5, Math.toIntExact((long) uses.get("use_type_id")));
			this.dbm.execUpdate();
		}

		return this.dbm.getUpdateResult();
	}

	public int populateUseTypesTable() throws ParseException, IOException, SQLException {
		this.dbm.setQuery("INSERT INTO USE_TYPES VALUES (?, ?)");
		this.dbm.prepareStmt();

		for (Object o : this.getUseTypesJSONArray()) {
			JSONObject types = (JSONObject) o;

			this.dbm.setIntBindVariable(1, Math.toIntExact((long) types.get("id")));
			this.dbm.setStringBindVariable(2, (String) types.get("name"));
			this.dbm.execUpdate();
		}

		return this.dbm.getUpdateResult();
	}

	private JSONArray getSitesJSONArray() throws ParseException, IOException {
		JSONParser jsonParser = new JSONParser();
		File file = ResourceUtils.getFile(this.JSON_PATH + this.SITE);

		return (JSONArray) jsonParser.parse(new String(Files.readAllBytes(file.toPath())));
	}

	private JSONArray getSiteUsesJSONArray() throws ParseException, IOException {
		JSONParser jsonParser = new JSONParser();
		File file = ResourceUtils.getFile(this.JSON_PATH + this.SITE_USES);

		return (JSONArray) jsonParser.parse(new String(Files.readAllBytes(file.toPath())));
	}

	private JSONArray getUseTypesJSONArray() throws ParseException, IOException {
		JSONParser jsonParser = new JSONParser();
		File file = ResourceUtils.getFile(this.JSON_PATH + this.USE_TYPES);

		return (JSONArray) jsonParser.parse(new String(Files.readAllBytes(file.toPath())));
	}
}
