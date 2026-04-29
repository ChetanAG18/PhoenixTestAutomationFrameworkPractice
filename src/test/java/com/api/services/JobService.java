package com.api.services;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import com.api.constants.Role;

import io.restassured.response.Response;

public class JobService {

	private static final String CREATEJOB_ENDPOINT = "/job/create";

	public Response create(Role role, Object payload) {
		return given().spec(requestSpecWithAuth(role, payload)).when().post(CREATEJOB_ENDPOINT);
	}

}
