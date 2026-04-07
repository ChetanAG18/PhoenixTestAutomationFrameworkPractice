package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.api.request.models.CreateJobPayload;
import com.api.request.models.UserCredentials;
import com.api.utils.CSVReaderUtil;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.FakerDataGenerator;
import com.api.utils.JsonReaderUtil;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtils {
	
	@DataProvider(name = "LoginAPIDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIDataProvider() {
		return CSVReaderUtil.loadCSV("testData/LoginCreds.csv", UserBean.class);
	}
	
	
	@DataProvider(name = "CreateJobAPIDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIDataProvider() {
		Iterator<CreateJobBean> createJobBeanIterator = CSVReaderUtil.loadCSV("testData/CreateJobData.csv", CreateJobBean.class);
		
		List<CreateJobPayload> createJobPayloadList = new ArrayList<CreateJobPayload>();
		CreateJobBean tempBean;
		CreateJobPayload tempPayload;
		while(createJobBeanIterator.hasNext()) {
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
		Iterator<CreateJobPayload> payloadIterator = FakerDataGenerator.generateFakeCreateJobData(fakerCountInt);
		return payloadIterator;
	}
	
	@DataProvider(name = "LoginAPIJSONDataProvider", parallel = true)
	public static Iterator<UserCredentials> loginAPIJSONDataProvider() {
		return JsonReaderUtil.loadJSON("testData/LoginAPITestData.json", UserCredentials[].class);
	}
	
	@DataProvider(name = "CreateJobAPIJSONDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIJSONDataProvider() {
		return JsonReaderUtil.loadJSON("testData/CreateJobAPITestData.json", CreateJobPayload[].class);
	}

}
