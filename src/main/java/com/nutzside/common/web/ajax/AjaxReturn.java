package com.nutzside.common.web.ajax;

public class AjaxReturn {
	
	int statusCode;
	String message;
	Object data;


	public int getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public AjaxReturn setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	public AjaxReturn setMessage(String message) {
		this.message = message;
		return this;
	}



	public AjaxReturn setData(Object data) {
		this.data = data;
		return this;
	}

}