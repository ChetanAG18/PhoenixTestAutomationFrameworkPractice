package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.poiji.bind.Poiji;

public class ExcelReaderUtil {
	
	private static final Logger LOGGER = LogManager.getLogger(ExcelReaderUtil.class);

	private ExcelReaderUtil() {

	}

	public static <T> Iterator<T> loadTestData(String pathToExcelFile, String sheetName, Class<T> clazz) {
		
		LOGGER.info("Reading the testdata from .xlsx file and sheet name", pathToExcelFile, sheetName);
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathToExcelFile);

		XSSFWorkbook xssfWorkbook = null;
		try {
			xssfWorkbook = new XSSFWorkbook(is);
		} catch (IOException e) {
			LOGGER.error("Cannot read the test data from excel file {}", pathToExcelFile, e);
			e.printStackTrace();
		}

		XSSFSheet xssfSheet = xssfWorkbook.getSheet(sheetName);
		
		LOGGER.info("Converting the XSSFSheet {} to POJO class of type {}", xssfSheet, clazz);
		List<T> dataList = Poiji.fromExcel(xssfSheet, clazz);

		return dataList.iterator();
	}

}
