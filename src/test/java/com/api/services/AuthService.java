package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class AuthService {

	private static final String LOGIN_ENDPOINT = "/login";

	public Response login(Object paylaod) {
		return given().spec(requestSpec(paylaod)).when().post(LOGIN_ENDPOINT);
	}

}
