package com.api.tests.datadriven;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.testng.annotations.Test;

import com.api.request.models.CreateJobPayload;

public class CreateJobAPIJSONDataDrivenTest {
	
	@Test(description = "Verify if the create job api able to create Inwarranty job",
			groups = {"api", "regression", "datadriven", "json"},
			dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider = "CreateJobAPIJSONDataProvider")
	public void createJobAPITest(CreateJobPayload createJobPayload) {
		
		
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
