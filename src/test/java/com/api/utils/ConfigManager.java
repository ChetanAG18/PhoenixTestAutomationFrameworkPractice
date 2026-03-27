package com.api.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	private static Properties prop = new Properties();
	private static String path = "config/config.properties";
	private static String env;

	private ConfigManager() {

	}

	static {

		env = System.getProperty("env", "qa");
		env = env.toLowerCase().trim();
		System.out.println("Running Tests in Env " + env);
		switch (env) {
		case "dev" -> path = "config/config.dev.properties";
		case "qa" -> path = "config/config.qa.properties";
		case "uat" -> path = "config/config.uat.properties";
		default -> {
			System.out.println("Invalid env '" + env + "', defaulting to QA");
			env = "qa";
			path = "config/config.qa.properties";
		}
		}

		System.out.println("Running Tests in Env " + env);

		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		if (inputStream == null) {
			throw new RuntimeException("Cannot Find the file at the path " + path);
		}
		try {
			prop.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {

		return prop.getProperty(key);
	}

}
