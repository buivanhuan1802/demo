package com.easywork.api.exception;

import org.springframework.http.HttpStatus;


public class IdentityNotGivenException extends ICustomException{

	public IdentityNotGivenException(HttpStatus status, String message, String[] errors, String url) {
		super(status, message, errors, url);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

}
