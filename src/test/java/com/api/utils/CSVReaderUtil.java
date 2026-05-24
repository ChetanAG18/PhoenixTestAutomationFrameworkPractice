package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import io.qameta.allure.Step;

public class CSVReaderUtil {
	
	private static final Logger LOGGER = LogManager.getLogger(CSVReaderUtil.class);

	private CSVReaderUtil() {

	}

	@Step("Loading the test data from the csv file")
	public static <T> Iterator<T> loadCSV(String pathToCSVFile, Class<T> bean) {
		
		LOGGER.info("Loading the csv file from the path {}", pathToCSVFile);
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathToCSVFile);
		InputStreamReader isr = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(isr);
		
		CsvToBean<T> csvToBean = new CsvToBeanBuilder(csvReader).
				withType(bean)
				.withIgnoreEmptyLine(true).build();

		List<T> list = csvToBean.parse();
		
		LOGGER.info("Converting the csv to bean class {}", bean);

		return list.iterator();
	}
}
