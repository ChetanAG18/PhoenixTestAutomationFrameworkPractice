package com.api.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.api.request.models.CreateJobPayload;
import com.api.request.models.Customer;
import com.api.request.models.CustomerAddress;
import com.api.request.models.CustomerProduct;
import com.api.request.models.Problems;
import com.github.javafaker.Faker;

public class FakerDataGenerator {

	private static Faker faker = new Faker(new Locale("en-IND"));
	private static final String COUNTRY = "India";
	private static final int MST_SERVICE_LOCATION_ID = 0;
	private static final int MST_PLATFORM_ID = 2;
	private static final int MST_WARRENTY_STATUS_ID = 1;
	private static final int MST_OEM_ID = 1;
	private static final int PRODUCT_ID = 1;
	private static final int MST_MODEL_ID = 1;

	private FakerDataGenerator() {

	}

	public static CreateJobPayload generateFakeCreateJobData() {

		Customer customer = generateFakeCustomerData();
		CustomerAddress customerAddress = generateFakeCustomerAddressData();
		CustomerProduct customerProduct = generateFakeCustomerProduct();
		List<Problems> problemList = generateFakeProblems();

		CreateJobPayload createJobPayload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID,
				MST_WARRENTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemList);

		return createJobPayload;
	}
	
	public static Iterator<CreateJobPayload> generateFakeCreateJobData(int count) {
		List<CreateJobPayload> createJobPayloadList = new ArrayList<CreateJobPayload>();

		for (int i = 0; i < count; i++) {
			Customer customer = generateFakeCustomerData();
			CustomerAddress customerAddress = generateFakeCustomerAddressData();
			CustomerProduct customerProduct = generateFakeCustomerProduct();
			List<Problems> problemList = generateFakeProblems();
			CreateJobPayload createJobPayload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID,
					MST_WARRENTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemList);
			createJobPayloadList.add(createJobPayload);
		}
		return createJobPayloadList.iterator();
	}

	private static List<Problems> generateFakeProblems() {
		String problemRemark = faker.lorem().sentence(5);

		Random random = new Random();
		int problemId = random.nextInt(27) + 1;

		Problems problems = new Problems(problemId, problemRemark);
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);

		return problemList;
	}

	private static CustomerProduct generateFakeCustomerProduct() {
		String dop = DateTimeUtil.getTimeWithDaysAgo(10);
		String imeiNumber = faker.numerify("##############");
		String popUrl = faker.internet().url();

		CustomerProduct customerProduct = new CustomerProduct(dop, imeiNumber, imeiNumber, imeiNumber, popUrl, PRODUCT_ID, MST_MODEL_ID);
		return customerProduct;
	}

	private static CustomerAddress generateFakeCustomerAddressData() {
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

	private static Customer generateFakeCustomerData() {
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
