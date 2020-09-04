package com.easywork.api.restexceptionhandling;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.easywork.api.exception.ICustomException;
import com.easywork.api.exception.NoResponseContentException;
import com.easywork.api.exception.UnkownException;

@ControllerAdvice
public class UnknownExceptionHandling {

	@ExceptionHandler(Exception.class)
	protected String unknowEcxception(Exception ex, HttpServletRequest req) {
		ICustomException i = new UnkownException(HttpStatus.NO_CONTENT,
				"You are getting unkown error, Please contact to system adminstrator to see what happen",
				new String[] { "Unkown exception" }, req.getRequestURI());
		return i.toJson().toString();

	}

	@ExceptionHandler(NoResponseContentException.class)
	protected String resouceNotAvailable(ICustomException ex) throws Exception {
		if (ex instanceof NoResponseContentException)
			return ((NoResponseContentException) ex).toJson().toString();

		throw new Exception();

	}

}
