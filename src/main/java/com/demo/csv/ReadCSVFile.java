package com.demo.csv;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class ReadCSVFile {

	public static void main(String[] args) throws IOException, CsvException {
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/LoginCreds.csv");
		InputStreamReader isr = new InputStreamReader(is);
		
//		File csvFile = new File("C:\\Users\\Admin\\Desktop\\SDET with Jatin\\Java Module\\PhoenixTestAutomationFrameworkPractice\\src\\main\\resources\\testData\\LoginCreds.csv");
//		FileReader fr = new FileReader(csvFile);
		CSVReader csvReader = new CSVReader(isr);
		
		List<String []> dataList = csvReader.readAll();
		
		for(String [] dataArray : dataList) {
			System.out.println(dataArray[0]);
			System.out.println(dataArray[1]);
		}
	}

}
