package com.api.services;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UserService {

	private static final String USER_DETAILS_ENDPOINT = "/userdetails";
	private static final Logger LOGGER = LogManager.getLogger(UserService.class);
	
	@Step("Making the user details api request with a role")
	public Response userdetails(Role role) {
		LOGGER.info("Making a request to {} with the role {}",USER_DETAILS_ENDPOINT, role);
		return given().spec(requestSpecWithAuth(role)).when().get(USER_DETAILS_ENDPOINT);
	}

}
