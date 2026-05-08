package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.api.utils.VaultDBConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {
	
	private static final Logger LOGGER = LogManager.getLogger(DatabaseManager.class);

	private static Connection connection;

	private static HikariConfig hikariConfig;
	private static HikariDataSource hikariDataSource;
	private static final int MAXIMUM_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));
	private static final int MINIMUM_IDLE = Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE"));
	private static final int CONNECTION_TIMEOUT_IN_SECONDS = Integer
			.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT_IN_SECONDS"));
	private static final int IDLE_TIMEOUT_IN_SECONDS = Integer
			.parseInt(ConfigManager.getProperty("IDLE_TIMEOUT_IN_SECONDS"));
	private static final int MAX_LIFE_TIME_IN_MINS = Integer
			.parseInt(ConfigManager.getProperty("MAX_LIFE_TIME_IN_MINS"));
	private static final String POOL_NAME = ConfigManager.getProperty("POOL_NAME");

	private static boolean isVaultUp = true;
	private static final String DB_URL = loadSecret("DB_URL");
	private static final String DB_USERNAME = loadSecret("DB_USERNAME");
	private static final String DB_PASSWORD = loadSecret("DB_PASSWORD");

	public static String loadSecret(String key) {
		String value = null;
		
		if (isVaultUp) {
			value = VaultDBConfig.getSecret(key);

			if (value == null) {
				LOGGER.error("Vault is Down!! or some issue with Vault");
				isVaultUp = false;
			} else {
				LOGGER.info("Reading the value for key {} from valut", key);
				return value;
			}
		}

		LOGGER.info("READING VALUE FOM ENV.....");
		value = EnvUtil.getValue(key);
		return value;
	}

	private DatabaseManager() {

	}

	private static void instanciatePool() {

		if (hikariDataSource == null) {
			LOGGER.warn("Database connection is not available.... Creating HikariDataSource");
			synchronized (DatabaseManager.class) {
				if (hikariDataSource == null) {
					hikariConfig = new HikariConfig();
					hikariConfig.setJdbcUrl(DB_URL);
					hikariConfig.setUsername(DB_USERNAME);
					hikariConfig.setPassword(DB_PASSWORD);
					hikariConfig.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
					hikariConfig.setMinimumIdle(MINIMUM_IDLE);
					hikariConfig.setConnectionTimeout(CONNECTION_TIMEOUT_IN_SECONDS * 1000); // 10 Sec
					hikariConfig.setIdleTimeout(IDLE_TIMEOUT_IN_SECONDS * 1000); // 10 Sec
					hikariConfig.setMaxLifetime(MAX_LIFE_TIME_IN_MINS * 60 * 1000); // 30 * 60 * 1000 --> 30 mins
					hikariConfig.setPoolName(POOL_NAME);

					hikariDataSource = new HikariDataSource(hikariConfig);
					LOGGER.info("Hikari Data Source is Created!!!");
				}
			}
		}

	}

	public static Connection getConnection() throws SQLException {
		if (hikariDataSource == null) {
			LOGGER.info("Initializing the Database Connection using HikariCP!!");
			instanciatePool();
		}

		else if (hikariDataSource.isClosed()) {
			LOGGER.error("HIKARI DATA SOURCE IS CLOSED");
			throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
		}

		connection = hikariDataSource.getConnection();

		return connection;

	}

}
