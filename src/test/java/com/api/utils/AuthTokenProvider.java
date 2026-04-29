package com.api.utils;

import static com.api.constants.Role.ENG;
import static com.api.constants.Role.FD;
import static com.api.constants.Role.QC;
import static com.api.constants.Role.SUP;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.api.constants.Role;
import com.api.request.models.UserCredentials;

import io.restassured.http.ContentType;

public class AuthTokenProvider {
	
	private static Map<Role, String> tokenCahce = new ConcurrentHashMap<Role, String>();
	
	private AuthTokenProvider() {
		
	}

	public static String getToken(Role role) {
		
		if(tokenCahce.containsKey(role)) {
			return tokenCahce.get(role);
		}
		
		UserCredentials userCredentials = null;
		if (role == FD) {
			userCredentials = new UserCredentials("iamfd", "password");
		} else if (role == SUP) {
			userCredentials = new UserCredentials("iamsup", "password");
		} else if (role == ENG) {
			userCredentials = new UserCredentials("iameng", "password");
		} else if (role == QC) {
			userCredentials = new UserCredentials("iamqa", "password");
		}
		
		String token = given()
		.baseUri(ConfigManager.getProperty("BASE_URI"))
		.contentType(ContentType.JSON)
		.body(userCredentials)
		.when().post("login")
		.then().log().ifValidationFails()
		.statusCode(200)
		.body("message", equalTo("Success"))
		.extract().body().jsonPath().getString("data.token");
		
		tokenCahce.put(role, token);
		
		return token;
	}

}
