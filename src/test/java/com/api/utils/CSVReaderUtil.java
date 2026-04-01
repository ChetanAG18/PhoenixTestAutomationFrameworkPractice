package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.dataproviders.api.bean.UserBean;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVReaderUtil {

	private CSVReaderUtil() {

	}

	public static void loadCSV(String pathToCSVFile) {

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathToCSVFile);
		InputStreamReader isr = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(isr);

		CsvToBean<UserBean> csvToBean = new CsvToBeanBuilder(csvReader).withType(com.dataproviders.api.bean.UserBean.class)
				.withIgnoreEmptyLine(true).build();

		List<UserBean> userList = csvToBean.parse();

		System.out.println(userList);
	}
}
