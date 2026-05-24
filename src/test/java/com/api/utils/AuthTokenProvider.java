package com.api.utils;

import static com.api.constants.Role.ENG;
import static com.api.constants.Role.FD;
import static com.api.constants.Role.QC;
import static com.api.constants.Role.SUP;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;
import com.api.request.models.UserCredentials;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;

public class AuthTokenProvider {
	
	private static Map<Role, String> tokenChache = new ConcurrentHashMap<Role, String>();
	private static final Logger LOGGER = LogManager.getLogger(AuthTokenProvider.class);
	
	private AuthTokenProvider() {
		
	}

	@Step("Getting the authtoken for the role")
	public static String getToken(Role role) {
		
		LOGGER.info("Checking if the token is present in the cache for role {}", role);
		if (tokenChache.containsKey(role)) {
			LOGGER.info("Token found in the cache");
			return tokenChache.get(role);
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
		
		LOGGER.info("Token not found, making the login api request to generate the token");
		
		String token = given()
		.baseUri(ConfigManager.getProperty("BASE_URI"))
		.contentType(ContentType.JSON)
		.body(userCredentials)
		.when().post("login")
		.then().log().ifValidationFails()
		.statusCode(200)
		.body("message", equalTo("Success"))
		.extract().body().jsonPath().getString("data.token");
		
		if(token == null) {
			LOGGER.info("Not able to generate the token");
		} else {
			LOGGER.info("Token generated and Stored the token in the cache for future requests");
		}		
		
		tokenChache.put(role, token);
		
		return token;
	}

}
