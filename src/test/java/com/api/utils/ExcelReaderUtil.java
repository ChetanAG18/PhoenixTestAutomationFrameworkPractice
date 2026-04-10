package com.api.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtil {

	public static void main(String[] args) throws IOException {
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/PhoenixTestData.xlsx");
		
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		XSSFSheet xssfSheet = xssfWorkbook.getSheet("LoginTestData");
		XSSFRow xssfRow;
		XSSFCell xssfCell;
		
		//System.out.println(xssfCell);
		
		int lastRowIndex = xssfSheet.getLastRowNum();
		
		XSSFRow rowHeader = xssfSheet.getRow(0);
		int lastCellIndex = rowHeader.getLastCellNum()-1;
		
		for(int rowIndex = 0; rowIndex <= lastRowIndex; rowIndex++) {
			for(int colIndex = 0; colIndex <= lastCellIndex; colIndex++) {
				xssfRow = xssfSheet.getRow(rowIndex);
				xssfCell = xssfRow.getCell(colIndex);
				System.out.print(xssfCell + " ");
			}
			System.out.println();
		}
	}

}
