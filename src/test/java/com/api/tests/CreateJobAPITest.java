package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import static com.api.utils.SpecUtil.*;

import static com.api.constants.Role.*;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

public class CreateJobAPITest {
	
	
	
	@Test
	public void reateJobAPITest() {
		
		Customer customer = new Customer("Chetan", "AG", "7090191755", "", "agchetan18@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("D 404", "Vasant Galaxy", "Mangalawar Pet", "Inorbit", "Laxmi Nagar", "587311", "India", "Karnataka");
		CustomerProduct customerProduct = new CustomerProduct("2026-01-24T18:30:00.000Z", "26942811903253", "26942811903253", "26942811903253", "2026-01-24T18:30:00.000Z", 1, 1);
		Problems problems = new Problems(1, "Battery Issue");
		Problems problemsArray [] = new Problems[1];
		problemsArray[0] = problems;
		
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsArray);
		
		given()
			.spec(requestSpecWithAuth(FD, createJobPayload))
			.when()
				.post("/job/create")
			.then()
				.spec(responseSpec_OK())
				.body("message", equalTo("Job created successfully. "))
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/CreateJobAPIResponseSchema.json"));
	}

}
