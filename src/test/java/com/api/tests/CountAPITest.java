package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static com.api.constants.Role.*;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class CountAPITest {

	@Test
	public void verifyCountAPIResponse() {
		given()
			.baseUri(ConfigManager.getProperty("BASE_URI"))
		.and()
			.header("Authorization", AuthTokenProvider.getToken(FD))
			.log().uri()
			.log().method()
			.log().headers()
		.when()
			.get("/dashboard/count")
		.then()
			.log().all()
			.statusCode(200)
			.time(lessThan(1000L))
			.body("message", equalTo("Success"))
			.body("data", notNullValue())
			.body("data.size()", equalTo(3))
			.body("data.count", everyItem(greaterThanOrEqualTo(0)))
			.body("data.label", everyItem(not(blankOrNullString())))
			.body("data.key", containsInAnyOrder("pending_for_delivery", "created_today", "pending_fst_assignment"))
			.body(matchesJsonSchemaInClasspath("responseSchema/CountAPIResponseSchema.json"));
	}
	
	@Test
	public void countAPITest_MissingAuthToken() {
		given()
			.baseUri(ConfigManager.getProperty("BASE_URI"))
		.and()
			.log().uri()
			.log().method()
			.log().headers()
		.when()
			.get("/dashboard/count")
		.then()
			.log().all()
		    .statusCode(401);
	}
	
	
	
	
}
