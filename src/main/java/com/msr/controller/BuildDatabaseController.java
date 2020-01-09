package com.msr.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msr.util.DBManager;
import com.msr.util.DataManager;

@RestController
@RequestMapping("/buildDatabase")
public class BuildDatabaseController {
    private DBManager dbm;
    private final String SQL_PATH = "/queries/";

    @GetMapping("/buildTables")
    public int buildTables() throws SQLException, ClassNotFoundException {
	this.dbm = new DBManager();
	this.dbm.setSqlPath(this.SQL_PATH);
	this.dbm.setSqlFile("BuildTables.sql");
	this.dbm.getQueryFromFile();
	this.dbm.prepareStmt();
	this.dbm.execUpdate();

	return this.dbm.getUpdateResult();
    }

    @GetMapping("/populateTables")
    public int populateTables() throws ParseException, IOException, SQLException {
	DataManager dm = new DataManager(this.dbm);
	int sites = dm.populateSiteTable();
	int uses = dm.populateSiteUsesTable();
	int types = dm.populateUseTypesTable();

	if (this.dbm.getStmt() != null) {
	    this.dbm.closeResultSet();
	}

	if (this.dbm.getConnection() != null) {
	    this.dbm.getConnection().close();
	}

	if (sites > 0 && uses > 0 && types > 0) {
	    return (sites + uses + types);
	} else {
	    return 0;
	}
    }
}
