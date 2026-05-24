package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static com.api.utils.SpecUtil.responseSpec_TEXT;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.services.DashboardService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners(com.listeners.APITestListener.class)
@Epic("Job Management")
@Feature("Job Count")
public class CountAPITest {
	
	private DashboardService dashboardService;
	
	@BeforeMethod(description = "Instanting the DashboardService object")
	public void setup() {
		dashboardService = new DashboardService();
	}
	
	@Story("Job Count should be shown correctly")
	@Description("Verify if the count API response is giving correct response")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Verify if the count API response is giving correct response", groups = {"api", "regression", "smoke"})
	public void verifyCountAPIResponse() {
		dashboardService.count(FD)
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.body("data", notNullValue())
			.body("data.size()", equalTo(3))
			.body("data.count", everyItem(greaterThanOrEqualTo(0)))
			.body("data.label", everyItem(not(blankOrNullString())))
			.body("data.key", containsInAnyOrder("pending_for_delivery", "created_today", "pending_fst_assignment"))
			.body(matchesJsonSchemaInClasspath("responseSchema/CountAPIResponseSchema.json"));
	}
	
	@Test(description = "Verify if the count API response is giving correct status code for invalid token", groups = {"api", "negative", "regression", "smoke"})
	public void countAPITest_MissingAuthToken() {
		dashboardService.countWithNoAuth()
		.then()
			.spec(responseSpec_TEXT(401));
	}
	
	
	
	
}
