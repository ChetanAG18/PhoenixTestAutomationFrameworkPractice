package com.api.services;

import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;
import static com.api.utils.SpecUtil.*;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class DashboardService {

	private static final String COUNT_ENDPOINT = "/dashboard/count";
	
	private static final String DETAILS_ENDPOINT = "/dashboard/details";
	
	private static final Logger LOGGER = LogManager.getLogger(DashboardService.class);
	
	@Step("Making the count api request with a role")
	public Response count(Role role) {
		LOGGER.info("Making a request to {} with the role {}",COUNT_ENDPOINT, role);
		return given().spec(requestSpecWithAuth(role)).when().get(COUNT_ENDPOINT);
	}
	
	@Step("Making the count api request without auth token")
	public Response countWithNoAuth() {
		LOGGER.info("Making a request to {} with no auth token",COUNT_ENDPOINT);
		return given().spec(requestSpec()).when().get(COUNT_ENDPOINT);
	}
	
	@Step("Making the details api request")
	public Response details(Role role, Object payload) {
		LOGGER.info("Making a request to {} with the role {} and payload {}",DETAILS_ENDPOINT, role, payload);
		return given().spec(requestSpecWithAuth(role)).body(payload).when().post(DETAILS_ENDPOINT);
	}

}
