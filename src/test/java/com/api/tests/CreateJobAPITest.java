package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;

public class CreateJobAPITest {
	
	@Test
	public void reateJobAPITest() {
		
		Customer customer = new Customer("Chetan", "AG", "7090191755", "", "agchetan18@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("D 404", "Vasant Galaxy", "Mangalawar Pet", "Inorbit", "Laxmi Nagar", "587311", "India", "Karnataka");
		CustomerProduct customerProduct = new CustomerProduct("2026-01-24T18:30:00.000Z", "12342811903253", "12342811903253", "12342811903253", "2026-01-24T18:30:00.000Z", 1, 1);
		Problems problems = new Problems(1, "Battery Issue");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
		
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsList);
		
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
