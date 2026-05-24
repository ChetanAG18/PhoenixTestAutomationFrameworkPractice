package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
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
import com.api.services.JobService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners(com.listeners.APITestListener.class)
@Epic("Job Management")
@Feature("Create Job")
public class CreateJobAPITest {
	
	private CreateJobPayload createJobPayload;
	private JobService jobService ;
	
	@BeforeMethod(description = "Creating createjob api request paylaod and instantiating the JobService object")
	public void setUp() {
		Customer customer = new Customer("Chetan", "AG", "7090191755", "", "agchetan18@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("D 404", "Vasant Galaxy", "Mangalawar Pet", "Inorbit", "Laxmi Nagar", "587311", "India", "Karnataka");
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "98742879627273", "98742879627273", "98742879627273", getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
		
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), WarrantyStatus.IN_WARRENTY.getCode(), Oem.GOOGLE.getCode(), customer, customerAddress, customerProduct, problemsList);
		
		jobService = new JobService();
	}
	
	@Story("FD should be able to create the job")
	@Description("Verifying if FD is able to use create job api and Inwarranty job is created")
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Verifying if FD is able to use create job api and Inwarranty job is created", groups = {"api", "regression", "smoke"})
	public void createJobAPITest() {
		
		
		    jobService.create(FD, createJobPayload)
			.then()
				.spec(responseSpec_OK())
				.body("message", equalTo("Job created successfully. "))
				.body(matchesJsonSchemaInClasspath("responseSchema/CreateJobAPIResponseSchema.json"))
				.body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"));
	}

}
