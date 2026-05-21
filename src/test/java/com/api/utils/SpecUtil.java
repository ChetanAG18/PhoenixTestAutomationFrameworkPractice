package com.api.utils;

import static org.hamcrest.Matchers.*;

import com.api.constants.Role;
import com.api.filter.SensitiveDataFilter;

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
		.addFilter(new SensitiveDataFilter())
		.build();
		
		return requestSpecification;
	}
	
	public static RequestSpecification requestSpec(Object payload) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(JSON)
		.setAccept(JSON)
		.setBody(payload)
		.addFilter(new SensitiveDataFilter())
		.build();
		
		return requestSpecification;
	}
	
	
	public static RequestSpecification requestSpecWithAuth(Role role) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(ConfigManager.getProperty("BASE_URI"))
				.setContentType(JSON)
				.setAccept(JSON)
				.addHeader("Authorization", getToken(role))
				.addFilter(new SensitiveDataFilter())
				.build();
				
				return requestSpecification;
	}
	
	public static RequestSpecification requestSpecWithAuth(Role role, Object payload) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(ConfigManager.getProperty("BASE_URI"))
				.setContentType(JSON)
				.setAccept(JSON)
				.addHeader("Authorization", getToken(role))
				.setBody(payload)
				.addFilter(new SensitiveDataFilter())
				.build();
				
				return requestSpecification;
	}
	
	public static ResponseSpecification responseSpec_OK() {
		ResponseSpecification responseSpecification =  new ResponseSpecBuilder()
		.expectContentType(JSON)
		.expectStatusCode(200)
		.expectResponseTime(lessThan(2000L))
		.build();
		
		return responseSpecification;
	}
	
	public static ResponseSpecification responseSpec_JSON(int statusCode) {
		ResponseSpecification responseSpecification =  new ResponseSpecBuilder()
		.expectContentType(JSON)
		.expectStatusCode(statusCode)
		.expectResponseTime(lessThan(2000L))
		.build();
		
		return responseSpecification;
	}
	
	public static ResponseSpecification responseSpec_TEXT(int statusCode) {
		ResponseSpecification responseSpecification =  new ResponseSpecBuilder()
		.expectStatusCode(statusCode)
		.expectResponseTime(lessThan(2000L))
		.build();
		
		return responseSpecification;
	}
}
