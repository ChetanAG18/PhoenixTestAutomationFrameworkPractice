package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.services.MasterService;

public class MasterAPITest {
	
	private MasterService masterService;
	
	@BeforeMethod(description = "Instanting the MasterService object")
	public void setup() {
		masterService = new MasterService();
	}
	
	@Test(description = "Verify if the master API response is giving correct response", groups = {"api", "regression", "smoke"})
	public void masterAPITest() {
		masterService.master(FD)
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.body("data", notNullValue())
			.body("data", hasKey("mst_oem"))
			.body("data", hasKey("mst_model"))
			.body("$", hasKey("data"))
			.body("$", hasKey("message"))
			.body("data.mst_oem.size()", equalTo(2))
			.body("data.mst_model.size()", greaterThan(0))
			.body("data.mst_oem.id", everyItem(notNullValue()))
			.body("data.mst_oem.name", everyItem(notNullValue()))
			.body(matchesJsonSchemaInClasspath("responseSchema/MasterAPIResponseSchema.json"));
			
	}
	
	@Test(description = "Verify if the master API response is giving correct status code for invalid token", groups = {"api", "negative", "regression", "smoke"})
	public void invalidTokenMasterAPITest() {
		masterService.masterWithNoAuth()
		.then()
			.spec(responseSpec_TEXT(401));
	}
}
