package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.models.UserCredentials;
import com.dataproviders.api.bean.UserBean;
import com.poiji.bind.Poiji;

public class ExcelReaderUtil3 {

	private ExcelReaderUtil3() {

	}

	public static <T> Iterator<T> loadTestData(String pathToExcelFile, String sheetName, Class<T> clazz) {

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathToExcelFile);

		XSSFWorkbook xssfWorkbook = null;
		try {
			xssfWorkbook = new XSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		XSSFSheet xssfSheet = xssfWorkbook.getSheet(sheetName);

		List<T> dataList = Poiji.fromExcel(xssfSheet, clazz);

		return dataList.iterator();
	}

}
