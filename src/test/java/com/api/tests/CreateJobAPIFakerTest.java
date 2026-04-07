package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.models.CreateJobPayload;
import com.api.utils.FakerDataGenerator;

public class CreateJobAPIFakerTest {
	public static final String COUNTRY = "India";
	private CreateJobPayload createJobPayload;
	
	@BeforeMethod(description = "Creating createjob api request paylaod")
	public void setUp() {

		createJobPayload = FakerDataGenerator.generateFakeCreateJobData();

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
