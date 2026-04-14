package com.database;

import java.sql.Connection;
import java.sql.SQLException;

public class DemoRunner_CheckingOfHikariDatabaseManager {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = DatabaseManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(conn);
	}

}
