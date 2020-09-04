package com.easywork.api.exception;

import org.springframework.http.HttpStatus;

public class UnkownException extends ICustomException {

	public UnkownException(HttpStatus status, String message, String[] errors, String url) {
		super(status, message, errors, url);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
