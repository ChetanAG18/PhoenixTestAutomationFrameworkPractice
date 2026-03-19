package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.AuthTokenProvider.getToken;
import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class MasterAPITest {
	
	@Test
	public void masterAPITest() {
		given()
			.baseUri(getProperty("BASE_URI"))
		.and()
			.header("Authorization", getToken(FD))
		.and()
			.contentType("")
			.log().all()
		.when()
			.post("master")
		.then()
			.log().all()
			.statusCode(200)
			.time(lessThan(1500L))
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
	
	@Test
	public void invalidTokenMasterAPITest() {
		given()
			.baseUri(getProperty("BASE_URI"))
		.and()
			.contentType("")
			.log().all()
		.when()
			.post("master")
		.then()
			.log().all()
			.statusCode(401);
	}
}
