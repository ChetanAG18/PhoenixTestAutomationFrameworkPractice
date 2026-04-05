package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constants.Model;
import com.api.constants.Oem;
import com.api.constants.Platform;
import com.api.constants.Problem;
import com.api.constants.Product;
import com.api.constants.ServiceLocation;
import com.api.constants.WarrantyStatus;
import com.api.request.models.CreateJobPayload;
import com.api.request.models.Customer;
import com.api.request.models.CustomerAddress;
import com.api.request.models.CustomerProduct;
import com.api.request.models.Problems;
import com.api.utils.DateTimeUtil;
import com.github.javafaker.Faker;

public class CreateJobAPITest2 {
	public static final String COUNTRY = "India";
	private CreateJobPayload createJobPayload;
	
	@BeforeMethod(description = "Creating createjob api request paylaod")
	public void setUp() {
		Faker faker = new Faker(new Locale("en-IND"));

		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String mobileNumber = faker.numerify("70########");
		String alternativeMobileNumber = faker.numerify("70########");
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

		createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemList);
		System.out.println(createJobPayload);
		
	}
	
	@Test(description = "Verify if the create job api able to create Inwarranty job", groups = {"api", "regression", "smoke"})
	public void createJobAPITest() {
		
		
		given()
			.spec(requestSpecWithAuth(FD, createJobPayload))
			.when()
				.post("/job/create")
			.then()
				.spec(responseSpec_OK())
				.body("message", equalTo("Job created successfully. "))
				.body(matchesJsonSchemaInClasspath("responseSchema/CreateJobAPIResponseSchema.json"))
				.body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"));
	}

}
