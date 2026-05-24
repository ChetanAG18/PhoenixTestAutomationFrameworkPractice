package com.api.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.request.models.CreateJobPayload;
import com.api.request.models.Customer;
import com.api.request.models.CustomerAddress;
import com.api.request.models.CustomerProduct;
import com.api.request.models.Problems;
import com.github.javafaker.Faker;

import io.qameta.allure.Step;

public class FakerDataGenerator {

	private static Faker faker = new Faker(new Locale("en-IND"));
	private static final String COUNTRY = "India";
	private static final Random RANDOM = new Random();
	private static final int MST_SERVICE_LOCATION_ID = 0;
	private static final int MST_PLATFORM_ID = 2;
	private static final int MST_WARRENTY_STATUS_ID = 1;
	private static final int MST_OEM_ID = 1;
	private static final int PRODUCT_ID = 1;
	private static final int MST_MODEL_ID = 1;
	
	private static final int validProblemsIDs [] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15, 16, 17, 19, 20, 22, 24, 26, 27, 28, 29};

	private static final Logger LOGGER = LogManager.getLogger(FakerDataGenerator.class);

	private FakerDataGenerator() {

	}

	@Step("Generating the fake payload for create job api")
	public static CreateJobPayload generateFakeCreateJobData() {
		
		LOGGER.info("Generating the fake payload for create job api");
		Customer customer = generateFakeCustomerData();
		CustomerAddress customerAddress = generateFakeCustomerAddressData();
		CustomerProduct customerProduct = generateFakeCustomerProduct();
		List<Problems> problemList = generateFakeProblemsList();

		CreateJobPayload createJobPayload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID,
				MST_WARRENTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemList);

		return createJobPayload;
	}
	
	@Step("Generating the multiple fake payload for create job api")
	public static Iterator<CreateJobPayload> generateFakeCreateJobData(int count) {
		
		LOGGER.info("Generating the fake {} payloads for create job api", count);
		List<CreateJobPayload> createJobPayloadList = new ArrayList<CreateJobPayload>();

		for (int i = 1; i <= count; i++) {
			Customer customer = generateFakeCustomerData();
			CustomerAddress customerAddress = generateFakeCustomerAddressData();
			CustomerProduct customerProduct = generateFakeCustomerProduct();
			List<Problems> problemList = generateFakeProblemsList();
			CreateJobPayload createJobPayload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID,
					MST_WARRENTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemList);
			createJobPayloadList.add(createJobPayload);
		}
		return createJobPayloadList.iterator();
	}

	@Step("Generating the fake problem list data for create job api")
	private static List<Problems> generateFakeProblemsList() {
		LOGGER.info("Generating the fake problem list for create job api payload");
		int count = RANDOM.nextInt(3) + 1;
		String problemRemark;
		int randomIndex;
		Problems problems;
		List<Problems> problemList = new ArrayList<Problems>();
		for (int i = 1; i <= count; i++) {
			problemRemark = faker.lorem().sentence(5);
			randomIndex = RANDOM.nextInt(validProblemsIDs.length);
			problems = new Problems(validProblemsIDs[randomIndex], problemRemark);			
			problemList.add(problems);
		}
		return problemList;
	}

	@Step("Generating the fake customer product data for create job api")
	private static CustomerProduct generateFakeCustomerProduct() {
		LOGGER.info("Generating the fake customer product info for create job api payload");
		String dop = DateTimeUtil.getTimeWithDaysAgo(10);
		String imeiNumber = faker.numerify("##############");
		String popUrl = faker.internet().url();

		CustomerProduct customerProduct = new CustomerProduct(dop, imeiNumber, imeiNumber, imeiNumber, popUrl, PRODUCT_ID, MST_MODEL_ID);
		return customerProduct;
	}

	@Step("Generating the fake customer address data for create job api")
	private static CustomerAddress generateFakeCustomerAddressData() {
		LOGGER.info("Generating the fake customer address info for create job api payload");
		String flatNumber = faker.numerify("###");
		String apartmentName = faker.address().streetName();
		String streetName = faker.address().streetName();
		String landmark = faker.address().streetName();
		String area = faker.address().streetName();
		String pincode = faker.number().numberBetween(10000, 99999) + "";
		String state = faker.address().state();

		CustomerAddress customerAddress = new CustomerAddress(flatNumber, apartmentName, streetName, landmark, area,
				pincode, COUNTRY, state);
		return customerAddress;
	}

	@Step("Generating the fake customer data for create job api")
	private static Customer generateFakeCustomerData() {
		LOGGER.info("Generating the fake customer info for create job api payload");
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String mobileNumber = faker.numerify("70########");
		String alternativeMobileNumber = faker.numerify("70########");
		String emailId = faker.internet().emailAddress();
		String alternativeEmailId = faker.internet().emailAddress();

		Customer customer = new Customer(firstName, lastName, mobileNumber, alternativeMobileNumber, emailId,
				alternativeEmailId);
		return customer;
	}

}
