package com.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPDemo {

	public static void main(String[] args) throws SQLException {
		HikariConfig hConfig = new HikariConfig();
		hConfig.setJdbcUrl(ConfigManager.getProperty("DB_URL"));
		hConfig.setUsername(ConfigManager.getProperty("DB_USERNAME"));
		hConfig.setPassword(ConfigManager.getProperty("DB_PASSWORD"));
		hConfig.setMaximumPoolSize(10);
		hConfig.setMinimumIdle(2);
		hConfig.setConnectionTimeout(10000); //10 Sec
		hConfig.setIdleTimeout(10000);
		hConfig.setMaxLifetime(1800000); //30 * 60 * 1000 --> 30 mins
		hConfig.setPoolName("Phoenix Test Automation Framework Pool");
		
		HikariDataSource hikariDataSource = new HikariDataSource(hConfig);
		 
		Connection conn = hikariDataSource.getConnection();
		
		Statement statement = conn.createStatement();
		
		ResultSet resultSet = statement.executeQuery("select tc.first_name, tc.last_name, tc.mobile_number  from tr_customer tc ");
		
		while(resultSet.next()) {
			System.out.println(resultSet.getString("first_name")+" "+resultSet.getString("last_name")+" "+resultSet.getString("mobile_number"));
		}
		
		hikariDataSource.close();
	}

}
