package com.api.services;

import static com.api.utils.SpecUtil.*;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;

import io.restassured.response.Response;

public class MasterService {

	private static final String MASTER_ENDPOINT = "/master";
	private static final Logger LOGGER = LogManager.getLogger(MasterService.class);

	public Response master(Role role) {
		LOGGER.info("Making a request to {} with the role {}",MASTER_ENDPOINT, role);
		return given().spec(requestSpecWithAuth(role)).when().post(MASTER_ENDPOINT);
	}
	
	public Response masterWithNoAuth() {
		LOGGER.info("Making a request to {} with no auth token",MASTER_ENDPOINT);
		return given().spec(requestSpec()).when().post(MASTER_ENDPOINT);
	}

}
