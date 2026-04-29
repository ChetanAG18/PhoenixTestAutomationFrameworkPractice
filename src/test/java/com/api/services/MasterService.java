package com.api.services;

import static com.api.utils.SpecUtil.*;
import static io.restassured.RestAssured.given;

import com.api.constants.Role;

import io.restassured.response.Response;

public class MasterService {

	private static final String MASTER_ENDPOINT = "/master";

	public Response master(Role role) {
		return given().spec(requestSpecWithAuth(role)).when().post(MASTER_ENDPOINT);
	}
	
	public Response masterWithNoAuth() {
		return given().spec(requestSpec()).when().post(MASTER_ENDPOINT);
	}

}
