package com.demo;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demo2 {
	
	private static final Logger LOGGER = LogManager.getLogger(Demo2.class);

	public static void main(String[] args) {
		
		LOGGER.info("Test Started");
		
		int a = 5;
		LOGGER.info("Value of a {}", a);
		
		int b = 10;
		LOGGER.info("Value of b {}", b);
		
		int result = a + b;
		LOGGER.info("Addition of a & b is {}",result);
		LOGGER.info("Test Ended");
	}

}
