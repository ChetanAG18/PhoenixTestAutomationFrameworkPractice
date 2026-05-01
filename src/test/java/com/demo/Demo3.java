package com.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demo3 {

	private static final Logger LOGGER = LogManager.getLogger(Demo3.class);

	public static void main(String[] args) {

		LOGGER.info("Test Started");

		int a = 5;
		LOGGER.info("Value of a {}", a);

		int b = 0;
		if (b == 0) {
			LOGGER.warn("Value of b {}", b);
		} else {
			LOGGER.info("Value of b {}", b);
		}

		try {
			int result = a / b;
			LOGGER.info("Devision of a & b is {}", result);
		} catch (ArithmeticException e) {
			LOGGER.error("Operation can not perform ", e);
		}

		LOGGER.info("Test Ended");
	}

}
