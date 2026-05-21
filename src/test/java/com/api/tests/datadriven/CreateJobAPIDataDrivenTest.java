package com.api.tests.datadriven;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.request.models.CreateJobPayload;
import com.api.services.JobService;

@Listeners(com.listeners.APITestListener.class)
public class CreateJobAPIDataDrivenTest {
	
	private JobService jobService ;

	@BeforeMethod(description = "Instantiating the JobService object")
	public void setUp() {
		
		jobService = new JobService();
	}
	
	@Test(description = "Verify if the create job api able to create Inwarranty job",
			groups = {"api", "regression", "datadriven", "csv"},
			dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider = "CreateJobAPIDataProvider")
	public void createJobAPITest(CreateJobPayload createJobPayload) {	
		
			jobService.create(FD, createJobPayload)
			.then()
				.spec(responseSpec_OK())
				.body("message", equalTo("Job created successfully. "))
				.body(matchesJsonSchemaInClasspath("responseSchema/CreateJobAPIResponseSchema.json"))
				.body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"));
	}

}
