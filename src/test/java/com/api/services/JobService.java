package com.api.services;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class JobService {

	private static final String CREATEJOB_ENDPOINT = "/job/create";
	private static final String SEARCHJOB_ENDPOINT = "/job/search";
	
	private static final Logger LOGGER = LogManager.getLogger(JobService.class);
	
	@Step("Creating Inwarranty Job with create job api")
	public Response create(Role role, Object payload) {
		LOGGER.info("Making a request to {} with the role {} and payload {}",CREATEJOB_ENDPOINT, role, payload);
		return given().spec(requestSpecWithAuth(role, payload)).when().post(CREATEJOB_ENDPOINT);
	}
		
	@Step("Making the search api request")
	public Response search(Role role, Object payload) {
		LOGGER.info("Making a request to {} with the role {} and payload {}",SEARCHJOB_ENDPOINT, role, payload);
		return given().spec(requestSpecWithAuth(role, payload)).when().post(SEARCHJOB_ENDPOINT);
	}

}
