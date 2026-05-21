package com.api.filter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SensitiveDataFilter implements Filter {
	
	private static final Logger LOGGER = LogManager.getLogger(SensitiveDataFilter.class);

	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {
		LOGGER.info("*************************** REQUEST DETAILS ***************************");
		LOGGER.info("BASE URI: {}", requestSpec.getURI());
		LOGGER.info("HTTP METHOD: {}", requestSpec.getMethod());
		redactRequestHeaders(requestSpec);
		redactPayload(requestSpec);
		Response response = ctx.next(requestSpec, responseSpec);
		LOGGER.info("*************************** RESPONSE DETAILS ***************************");
		LOGGER.info("STATUS CODE: {}", response.getStatusLine());
		LOGGER.info("RESPONSR TIME ms: {}", response.timeIn(TimeUnit.MILLISECONDS));
		LOGGER.info("RESPONSE HEADERS: \n {}", response.getHeaders());
		redactResponse(response);
		return response;
	}

	private void redactRequestHeaders(FilterableRequestSpecification requestSpec) {
		List<Header> requestHeadersList = requestSpec.getHeaders().asList();
		LOGGER.info("REQUEST HEADERS: ");
		for (Header header : requestHeadersList) {
			if(header.getName().equalsIgnoreCase("Authorization")) {
				LOGGER.info("{}={}", header.getName(), "\"[REDACTED]\"");
			} else {
				LOGGER.info("{}={}", header.getName(), header.getValue());
			}
		}
	}

	private void redactResponse(Response response) {
		String responseBody = response.getBody().asPrettyString();
		responseBody = responseBody.replaceAll("\"token\"\s*:\s*\"[^\"]+\"", "\"token\":\"[REDACTED]\"");
		LOGGER.info("Response Body: \n {}" , responseBody);
	}

	private void redactPayload(FilterableRequestSpecification requestSpec) {
		if (requestSpec.getBody() != null) {
			String requestBody = requestSpec.getBody().toString();
			requestBody = requestBody.replaceAll("\"password\"\s*:\s*\"[^\"]+\"", "\"password\":\"[REDACTED]\"");
			LOGGER.info("Request Body: \n {}", requestBody);
		}
	}

}
