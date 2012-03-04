package com.scirisk.riskanalyzer.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	static String CONNECTION_URL_PATTERN = "jdbc:mysql://%s/%s";
	private String host;
	private String database;
	private String user;
	private String password;

	public ConnectionFactory(String host, String database, String user, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.host = host;
			this.database = database;
			this.user = user;
			this.password = password;
		} catch (Exception e) {
			throw new IllegalStateException("Error while initializing connection factory.", e);
		}
	}
	
	//"jdbc:mysql://mysql-dpacak.jelastic.servint.net/risk_analyzer?"
	public Connection getConnection() {
		String connectionUrl = String.format(CONNECTION_URL_PATTERN, host, database);
		try {
			return DriverManager.getConnection(connectionUrl, user, password);
		} catch (SQLException e) {
			throw new IllegalStateException("Error while establishing connection to database: " + database, e);
		}
	}

}
