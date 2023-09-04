package com.jinhogate.web.model.enums;

public enum StatusEnum {

	SERVER_UP(
			"SERVER_UP"
	),
	SERVER_DOWN(
			"SERVER_DOWN"
	);

	private final String status;

	StatusEnum(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

}
