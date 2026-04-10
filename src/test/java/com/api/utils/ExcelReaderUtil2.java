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


public class ExcelReaderUtil2 {
	
	private ExcelReaderUtil2() {
		
	}

	public static Iterator<UserCredentials> loadTestData() {
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/PhoenixTestData.xlsx");
		
		XSSFWorkbook xssfWorkbook = null;
		try {
			xssfWorkbook = new XSSFWorkbook(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFSheet xssfSheet = xssfWorkbook.getSheet("LoginTestData");
		
		//Read the Excel file ----> Store in the ArrayList<UserCredentials>
		
		//I want to know the indexes for the username and password in our sheet
		
		XSSFRow headerRows = xssfSheet.getRow(0);   //header
		
		int usernameIndex = -1;
		int passwordIndex = -1;
		
		for(Cell cell:headerRows) {
			if(cell.getStringCellValue().trim().equalsIgnoreCase("username")) {
				usernameIndex = cell.getColumnIndex();
			}
			
			if(cell.getStringCellValue().trim().equalsIgnoreCase("password")) {
				passwordIndex = cell.getColumnIndex();
			}
		}
		
		System.out.println(usernameIndex + "  " +passwordIndex );
		
		int lastRowIndex = xssfSheet.getLastRowNum();
		XSSFRow rowData;
		UserCredentials userCredentials;
		List<UserCredentials> userList = new ArrayList<UserCredentials>();
		for(int rowIndex=1; rowIndex<=lastRowIndex; rowIndex++) {
			rowData = xssfSheet.getRow(rowIndex);
			userCredentials = new UserCredentials(rowData.getCell(usernameIndex).toString(), rowData.getCell(passwordIndex).toString());
			userList.add(userCredentials);
		}
		
		return userList.iterator();
	}

}
