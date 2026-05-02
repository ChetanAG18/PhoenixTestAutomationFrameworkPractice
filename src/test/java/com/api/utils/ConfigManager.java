package com.api.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigManager {

	private static Properties prop = new Properties();
	private static String path = "config/config.properties";
	private static String env;
	
	private static final Logger LOGGER = LogManager.getLogger(ConfigManager.class);

	private ConfigManager() {

	}

	static {
		LOGGER.info("Reading the env value passed from terminal");
		if(System.getProperty("env") == null) {
			LOGGER.warn("env value is not passed... using qa as env");
		}
		env = System.getProperty("env", "qa");
		env = env.toLowerCase().trim();
		switch (env) {
		case "dev" -> path = "config/config.dev.properties";
		case "qa" -> path = "config/config.qa.properties";
		case "uat" -> path = "config/config.uat.properties";
		default -> {
			LOGGER.warn("{} is a invalid env value, defaulting to QA", env);
			env = "qa";
			path = "config/config.qa.properties";
		}
		}

		LOGGER.info("Running Tests in Env {}", env);
		LOGGER.info("Using the properties file from the path {}", path);

		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		if (inputStream == null) {
			LOGGER.error("Cannot Find the file at the path {}", path);
			throw new RuntimeException("Cannot Find the file at the path {}" + path);
		}
		try {
			prop.load(inputStream);
		} catch (FileNotFoundException e) {
			LOGGER.error("Cannot find the file in the path {}", path, e);
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.error("Something went wrong ... Please check the file {}", path, e);
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {

		return prop.getProperty(key);
	}

}
