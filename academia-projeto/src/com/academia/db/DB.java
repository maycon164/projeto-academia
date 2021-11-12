package com.academia.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	public static Connection conn = null;

	public static Connection getConnection() {

		if (conn == null) {
			try {
	
				Properties props = loadProperties();
				conn = DriverManager.getConnection(props.getProperty("dburl"), props);
				return conn;

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return conn;
	}

	private static Properties loadProperties() {

		try (FileInputStream fs = new FileInputStream("academiadb.properties")) {

			Properties props = new Properties();
			props.load(fs);
			return props;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}