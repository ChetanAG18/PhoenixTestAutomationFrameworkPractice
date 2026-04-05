package com.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.api.request.models.CreateJobPayload;
import com.api.request.models.Customer;
import com.api.request.models.CustomerAddress;
import com.api.request.models.CustomerProduct;
import com.api.request.models.Problems;
import com.github.javafaker.Faker;

public class FakerDemo2 {

	public static final String COUNTRY = "India";

	public static void main(String[] args) {

		Faker faker = new Faker(new Locale("en-IND"));

		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String mobileNumber = faker.phoneNumber().phoneNumber();
		String alternativeMobileNumber = faker.phoneNumber().phoneNumber();
		String emailId = faker.internet().emailAddress();
		String alternativeEmailId = faker.internet().emailAddress();

		Customer customer = new Customer(firstName, lastName, mobileNumber, alternativeMobileNumber, emailId,
				alternativeEmailId);
		System.out.println(customer);

		String flatNumber = faker.numerify("###");
		String apartmentName = faker.address().streetName();
		String streetName = faker.address().streetName();
		String landmark = faker.address().streetName();
		String area = faker.address().streetName();
		String pincode = faker.number().numberBetween(10000, 99999) +"";
		String state = faker.address().state();

		CustomerAddress customerAddress = new CustomerAddress(flatNumber, apartmentName, streetName, landmark, area,
				pincode, COUNTRY, state);
		System.out.println(customerAddress);
		
		
		String dop = DateTimeUtil.getTimeWithDaysAgo(10);
		String imeiNumber = faker.numerify("##############");
		String popUrl = faker.internet().url();
		
		CustomerProduct customerProduct = new CustomerProduct(dop, imeiNumber, imeiNumber, imeiNumber, popUrl, 1, 1);
		
		System.out.println(customerProduct);
		
		String problemRemark = faker.lorem().sentence(5);
		
		Random random = new Random();
		int problemId = random.nextInt(26)+1;
		
		Problems problems = new Problems(problemId,problemRemark);
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);
		
		System.out.println(problemList);

		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemList);
		System.out.println(createJobPayload);
	}

}
