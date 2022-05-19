package com.graddletool.playload;

public class ApiResponse {
	private String message;
	private boolean success;

	

	public ApiResponse(String message, boolean status) {
		super();
		this.message = message;
		this.success = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isStatus() {
		return success;
	}

	public void setStatus(boolean status) {
		this.success = status;
	}

	@Override
	public String toString() {
		return "ApiResponse [message=" + message + ", success=" + success + "]";
	}

	
}
