package com.api.utils;

import static com.api.utils.AuthTokenProvider.getToken;
import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.lessThan;

import com.api.constants.Role;
import com.api.filter.SensitiveDataFilter;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {
	
	@Step("Setting up the BASEUI, Content Type & Accept Type as Aplication/JSON and attaching the SensitiveData filter")
	public static RequestSpecification requestSpec() {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(JSON)
		.setAccept(JSON)
		.addFilter(new SensitiveDataFilter())
		.addFilter(new AllureRestAssured())
		.build();
		
		return requestSpecification;
	}
	
	@Step("Setting up the BASEUI, Content Type & Accept Type as Aplication/JSON and attaching the SensitiveData filter")
	public static RequestSpecification requestSpec(Object payload) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(JSON)
		.setAccept(JSON)
		.setBody(payload)
		.addFilter(new SensitiveDataFilter())
		.addFilter(new AllureRestAssured())
		.build();
		
		return requestSpecification;
	}
	
	@Step("Setting up the BASEUI, Content Type & Accept Type as Aplication/JSON and attaching the SensitiveData filter for a role")
	public static RequestSpecification requestSpecWithAuth(Role role) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(ConfigManager.getProperty("BASE_URI"))
				.setContentType(JSON)
				.setAccept(JSON)
				.addHeader("Authorization", getToken(role))
				.addFilter(new SensitiveDataFilter())
				.addFilter(new AllureRestAssured())
				.build();
				
				return requestSpecification;
	}
	
	@Step("Setting up the BASEUI, Content Type & Accept Type as Aplication/JSON and attaching the SensitiveData filter for a role and attaching payload") 
	public static RequestSpecification requestSpecWithAuth(Role role, Object payload) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(ConfigManager.getProperty("BASE_URI"))
				.setContentType(JSON)
				.setAccept(JSON)
				.addHeader("Authorization", getToken(role))
				.setBody(payload)
				.addFilter(new SensitiveDataFilter())
				.addFilter(new AllureRestAssured())
				.build();
				
				return requestSpecification;
	}
	
	@Step("Expecting the response to have Content Type as Application/JSON, Status Code as 200 and Response time Less than 1000 ms") 
	public static ResponseSpecification responseSpec_OK() {
		ResponseSpecification responseSpecification =  new ResponseSpecBuilder()
		.expectContentType(JSON)
		.expectStatusCode(200)
		.expectResponseTime(lessThan(2000L))
		.build();
		
		return responseSpecification;
	}
	
	@Step("Expecting the response to have Content Type as Application/JSON, Response time Less than 1000 ms and status code") 
	public static ResponseSpecification responseSpec_JSON(int statusCode) {
		ResponseSpecification responseSpecification =  new ResponseSpecBuilder()
		.expectContentType(JSON)
		.expectStatusCode(statusCode)
		.expectResponseTime(lessThan(2000L))
		.build();
		
		return responseSpecification;
	}
	
	@Step("Expecting the response to have Content Type as TEXT, Response time Less than 1000 ms and status code") 
	public static ResponseSpecification responseSpec_TEXT(int statusCode) {
		ResponseSpecification responseSpecification =  new ResponseSpecBuilder()
		.expectStatusCode(statusCode)
		.expectResponseTime(lessThan(2000L))
		.build();
		
		return responseSpecification;
	}
}
