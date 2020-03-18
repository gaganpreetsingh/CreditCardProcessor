package com.gagan.creditcard.api.response;

public class ApiResponse {

	String status;
	Object data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean hasError() {
		return ResponseStatus.ERROR.getStatusName().equals(this.getStatus());
	}

	public String toString() {
		return this.data.toString();
	}
}
