package com.msr.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;

public class DBManager {
	private final String DRIVER = "org.h2.Driver";
	private final String URL = "jdbc:h2:mem:measurabl";
	private final String USER = "measurabl";
	private final String PW = "measurabl";

	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rset = null;

	private String sqlPath = "";
	private String sqlFile = "";
	private String query = "";
	private int updateResult = 0;

	public DBManager() throws ClassNotFoundException, SQLException {
		Class.forName(this.DRIVER);
		this.conn = DriverManager.getConnection(this.URL, this.USER, this.PW);
	}

	public String getSqlPath() { return this.sqlPath; }

	public void setSqlPath(String sqlPath) { this.sqlPath = sqlPath; }

	public String getSqlFile() { return this.sqlFile; }

	public void setSqlFile(String sqlFile) { this.sqlFile = sqlFile; }

	public String getQuery() { return this.query; }

	public void setQuery(String query) { this.query = query; }

	private void setUpdateResult(int updateResult) { this.updateResult = updateResult; }

	public int getUpdateResult() { return this.updateResult; }

	public ResultSet getRS() { return this.rset; }

	public Connection getConnection() { return this.conn; }
	
	public PreparedStatement getStmt() {return this.stmt;}

	public void getQueryFromFile() {
		InputStream queryIS = null;

		try {
			queryIS = DBManager.class.getResourceAsStream(this.getSqlPath() + this.getSqlFile());

			this.setQuery(IOUtils.toString(queryIS));
		} catch (IOException ioe) {
			throw new IllegalStateException("Failed to read SQL query", ioe);
		} finally {
			IOUtils.closeQuietly(queryIS);
		}
	}

	public void prepareStmt() throws SQLException { this.stmt = this.conn.prepareStatement(this.getQuery()); }

	public void setStringBindVariable(int position, String value) throws SQLException {
		this.stmt.setString(position, value);
	}

	public void setIntBindVariable(int position, int value) throws SQLException { this.stmt.setInt(position, value); }

	public void execQuery() throws SQLException { this.rset = this.stmt.executeQuery(); }

	public void execUpdate() throws SQLException { this.setUpdateResult(this.stmt.executeUpdate()); }

	public void closeResultSet() throws SQLException { this.stmt.close(); }

	public void closeStmt() throws SQLException { this.stmt.close(); }
}
