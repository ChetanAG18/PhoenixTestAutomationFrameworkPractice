package com.api.request.models;

public record CustomerAddress(
		String flat_number,
		String apartment_name,
		String street_name,
		String landmark,
		String area,
		String pincode,
		String country,
		String state) {
}
