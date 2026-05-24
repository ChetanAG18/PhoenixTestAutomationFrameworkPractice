package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import com.api.request.models.CreateJobPayload;
import com.api.utils.CSVReaderUtil;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.ExcelReaderUtil;
import com.api.utils.FakerDataGenerator;
import com.api.utils.JsonReaderUtil;
import com.database.dao.CreateJobPayloadDataDao;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtils {
	
	private static final Logger LOGGER = LogManager.getLogger(DataProviderUtils.class);


	@DataProvider(name = "LoginAPIDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIDataProvider() {
		LOGGER.info("Loading Data from the CSV file testData/LoginCreds.csv");
		return CSVReaderUtil.loadCSV("testData/LoginCreds.csv", UserBean.class);
	}

	@DataProvider(name = "CreateJobAPIDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIDataProvider() {
		LOGGER.info("Loading Data from the CSV file testData/CreateJobData.csv.csv");
		Iterator<CreateJobBean> createJobBeanIterator = CSVReaderUtil.loadCSV("testData/CreateJobData.csv",
				CreateJobBean.class);

		List<CreateJobPayload> createJobPayloadList = new ArrayList<CreateJobPayload>();
		CreateJobBean tempBean;
		CreateJobPayload tempPayload;
		while (createJobBeanIterator.hasNext()) {
			tempBean = createJobBeanIterator.next();
			tempPayload = CreateJobBeanMapper.mapper(tempBean);
			createJobPayloadList.add(tempPayload);
		}

		return createJobPayloadList.iterator();
	}

	@DataProvider(name = "CreateJobAPIFakerDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIFakerDataProvider() {
		String fakerCount = System.getProperty("fakerCount", "5");
		int fakerCountInt = Integer.parseInt(fakerCount);
		LOGGER.info("Generating the fake create job data with faker count");
		Iterator<CreateJobPayload> payloadIterator = FakerDataGenerator.generateFakeCreateJobData(fakerCountInt);
		return payloadIterator;
	}

	@DataProvider(name = "LoginAPIJSONDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIJSONDataProvider() {
		LOGGER.info("Loading Data from the JSON file testData/LoginAPITestData.json");
		return JsonReaderUtil.loadJSON("testData/LoginAPITestData.json", UserBean[].class);
	}

	@DataProvider(name = "CreateJobAPIJSONDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIJSONDataProvider() {
		LOGGER.info("Loading Data from the JSON file testData/CreateJobAPITestData.json");
		return JsonReaderUtil.loadJSON("testData/CreateJobAPITestData.json", CreateJobPayload[].class);
	}

	@DataProvider(name = "LoginAPIExcelDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIExcelDataProvider() {
		LOGGER.info("Loading Data from the Excel file testData/PhoenixTestData.xlsx and sheet is LoginTestData");
		return ExcelReaderUtil.loadTestData("testData/PhoenixTestData.xlsx", "LoginTestData", UserBean.class);
	}

	@DataProvider(name = "CreateJobAPIExcelDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIExcelDataProvider() {
		LOGGER.info("Loading Data from the Excel file testData/PhoenixTestData.xlsx and sheet is CreateJobTestData");
		Iterator<CreateJobBean> createJobBeanIterator = ExcelReaderUtil.loadTestData("testData/PhoenixTestData.xlsx",
				"CreateJobTestData", CreateJobBean.class);

		List<CreateJobPayload> createJobPayloadList = new ArrayList<CreateJobPayload>();
		CreateJobBean tempBean;
		CreateJobPayload tempPayload;
		while (createJobBeanIterator.hasNext()) {
			tempBean = createJobBeanIterator.next();
			tempPayload = CreateJobBeanMapper.mapper(tempBean);
			createJobPayloadList.add(tempPayload);
		}

		return createJobPayloadList.iterator();
	}

	@DataProvider(name = "CreateJobAPIDBDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIDBDataProvider() {
		LOGGER.info("Loading Data from the Database for create job payload");
		
		List<CreateJobBean> createJobBeanList = CreateJobPayloadDataDao.getCreateJobPayloadData();
		List<CreateJobPayload> createJobPayloadList = new ArrayList<CreateJobPayload>();

		for (CreateJobBean createJobBean : createJobBeanList) {
			CreateJobPayload createJobPayload = CreateJobBeanMapper.mapper(createJobBean);
			createJobPayloadList.add(createJobPayload);
		}

		return createJobPayloadList.iterator();
	}

}
