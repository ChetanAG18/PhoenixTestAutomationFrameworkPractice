package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dataproviders.api.bean.UserBean;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class AuthService {

	private static final String LOGIN_ENDPOINT = "/login";
	private static final Logger LOGGER = LogManager.getLogger(AuthService.class);
	
	@Step("Perform login request with user credentials")
	public Response login(Object paylaod) {
		LOGGER.info("Making request to {} with the payload {}", LOGIN_ENDPOINT, ((UserBean)paylaod).getUsername());
		return given().spec(requestSpec(paylaod)).when().post(LOGIN_ENDPOINT);
	}

}
