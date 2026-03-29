package com.api.constants;

public enum Product {
	NEXUS_2(1), GOOGLE(2);

	int code;

	Product(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
