package com.api.utils;

import java.util.Locale;

import com.github.javafaker.Faker;

public class FakerDemo {

	public static void main(String[] args) {
		Faker faker = new Faker(new Locale("en-IND"));
		
		String fname = faker.name().firstName();
		System.out.println(fname);
		
		String lname = faker.name().lastName();
		System.out.println(lname);
		
		String buildingNumber = faker.address().buildingNumber();
		System.out.println(buildingNumber);
		
		System.out.println(faker.address().city());
		System.out.println(faker.address().state());
		System.out.println(faker.address().streetAddress());
		System.out.println(faker.address().streetName());
		System.out.println(faker.address().zipCode());
		
		System.out.println(faker.number().digits(5));	
		System.out.println(faker.numerify("7090######"));		
		System.out.println(faker.numerify("#####"));
		
		
		System.out.println(faker.internet().emailAddress(fname));
		System.out.println(faker.phoneNumber().phoneNumber());
	}

}
