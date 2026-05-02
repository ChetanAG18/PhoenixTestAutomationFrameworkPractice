package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReaderUtil {
	
	private static final Logger LOGGER = LogManager.getLogger(JsonReaderUtil.class);

	public static <T> Iterator<T> loadJSON(String pathToJSONFile, Class<T[]> clazz) {
		
		LOGGER.info("Reading the json from file {}", pathToJSONFile);
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathToJSONFile);
		ObjectMapper objectMapper = new ObjectMapper();
		T[] classArray;
		List<T> list = null;
		try {
			LOGGER.info("Converting the json data to bean class {}", clazz);
			classArray = objectMapper.readValue(is, clazz);
			list = Arrays.asList(classArray);
		} catch (IOException e) {
			LOGGER.error("Cannot read the json data from file {}", pathToJSONFile);
			e.printStackTrace();
		}

		return list.iterator();
	}

}
