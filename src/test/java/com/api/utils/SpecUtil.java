package com.api.utils;

import static org.hamcrest.Matchers.*;

import com.api.constants.Role;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import static io.restassured.http.ContentType.*;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static com.api.utils.ConfigManager.*;
import static com.api.utils.AuthTokenProvider.*;

public class SpecUtil {
	
	public static RequestSpecification requestSpec() {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(JSON)
		.setAccept(JSON)
		.log(LogDetail.URI)
		.log(LogDetail.METHOD)
		.log(LogDetail.HEADERS)
		.log(LogDetail.BODY)
		.build();
		
		return requestSpecification;
	}
	
	public static RequestSpecification requestSpec(Object payload) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(JSON)
		.setAccept(JSON)
		.setBody(payload)
		.log(LogDetail.URI)
		.log(LogDetail.METHOD)
		.log(LogDetail.HEADERS)
		.log(LogDetail.BODY)
		.build();
		
		return requestSpecification;
	}
	
	
	public static RequestSpecification requestSpecWithAuth(Role role) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(ConfigManager.getProperty("BASE_URI"))
				.setContentType(JSON)
				.setAccept(JSON)
				.addHeader("Authorization", getToken(role))
				.log(LogDetail.URI)
				.log(LogDetail.METHOD)
				.log(LogDetail.HEADERS)
				.log(LogDetail.BODY)
				.build();
				
				return requestSpecification;
	}
	
	public static ResponseSpecification responseSpec_OK() {
		ResponseSpecification responseSpecification =  new ResponseSpecBuilder()
		.expectContentType(JSON)
		.log(LogDetail.ALL)
		.expectStatusCode(200)
		.expectResponseTime(lessThan(2000L))
		.build();
		
		return responseSpecification;
	}
	
	public static ResponseSpecification responseSpec_JSON(int statusCode) {
		ResponseSpecification responseSpecification =  new ResponseSpecBuilder()
		.expectContentType(JSON)
		.log(LogDetail.ALL)
		.expectStatusCode(statusCode)
		.expectResponseTime(lessThan(2000L))
		.build();
		
		return responseSpecification;
	}
	
	public static ResponseSpecification responseSpec_TEXT(int statusCode) {
		ResponseSpecification responseSpecification =  new ResponseSpecBuilder()
		.log(LogDetail.ALL)
		.expectStatusCode(statusCode)
		.expectResponseTime(lessThan(2000L))
		.build();
		
		return responseSpecification;
	}
}
