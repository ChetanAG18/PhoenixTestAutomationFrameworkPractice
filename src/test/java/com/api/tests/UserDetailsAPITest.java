package com.api.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.lessThan;

import org.testng.annotations.Test;

import static com.api.constants.Role.*;

import static com.api.utils.AuthTokenProvider.*;

import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;

public class UserDetailsAPITest {
	@Test
	public void userDetailsAPITest(){
		Header authHeader = new Header("Authorization", getToken(FD));
		
		given().baseUri(getProperty("BASE_URI"))
		.and()
		.header(authHeader)
		.and()
		.accept(ContentType.JSON)
		.log().uri()
		.log().method()
		.log().headers()
		.when()
		.get("userdetails")
		.then()
		.log().all()
		.statusCode(200)
		.time(lessThan(2000L))
		.body(matchesJsonSchemaInClasspath("responseSchema/UserDetailsResponseSchema.json"));
	}
	

}
