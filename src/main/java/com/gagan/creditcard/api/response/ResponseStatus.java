package com.gagan.creditcard.api.response;

public enum ResponseStatus {

	SUCCESS("success"), ERROR("error");
	String statusName;

	ResponseStatus(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusName() {
		return statusName;
	}

}

