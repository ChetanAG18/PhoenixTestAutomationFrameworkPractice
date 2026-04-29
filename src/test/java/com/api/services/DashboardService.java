package com.api.services;

import static io.restassured.RestAssured.given;

import com.api.constants.Role;
import static com.api.utils.SpecUtil.*;

import io.restassured.response.Response;

public class DashboardService {

	private static final String COUNT_ENDPOINT = "/dashboard/count";

	public Response count(Role role) {
		return given().spec(requestSpecWithAuth(role)).when().get(COUNT_ENDPOINT);
	}
	
	public Response countWithNoAuth() {
		return given().spec(requestSpec()).when().get(COUNT_ENDPOINT);
	}

}
