package com.api.tests;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.request.models.UserCredentials;

public class LoginAPITest {
	
	@Test
	public void loginAPITest() throws IOException {
		UserCredentials userCredentials = new UserCredentials("iamfd", "password");
		
		given()
			.spec(requestSpec(userCredentials))
		.when()
			.post("/login")
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.body(matchesJsonSchemaInClasspath("responseSchema/LoginResponseSchema.json"));
	}
}
