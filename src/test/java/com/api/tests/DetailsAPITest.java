package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_OK;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.request.models.Details;
import com.api.services.DashboardService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners(com.listeners.APITestListener.class)
@Epic("Job Management")
@Feature("Job Details")
public class DetailsAPITest {
	
	private Details detailsPayload;
	private DashboardService dashboardService;
	
	@BeforeMethod(description = "Creating the details api request paylaod and instantiating the DashboardService object")
	public void setUp() {
		detailsPayload = new Details("created_today");		
		dashboardService = new DashboardService();
	}
	
	@Story("Job Details should be shown correctly")
	@Description("Validating Details api request")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Validating Details api request", groups = {"regression", "smoke", "e2e", "api"})
	public void detailsAPIRequest() {
		dashboardService.details(FD, detailsPayload)
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.body("data", notNullValue());				
	}

}
