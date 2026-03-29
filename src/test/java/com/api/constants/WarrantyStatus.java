package com.api.constants;

public enum WarrantyStatus {
	 IN_WARRENTY(1), OUT_WARRENTY(2);

	int code;

	WarrantyStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
