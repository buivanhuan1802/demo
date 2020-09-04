package com.easywork.api.restexceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.easywork.api.exception.CustomerNotFoundException;
import com.easywork.api.exception.ICustomException;
import com.easywork.api.exception.IdentityNotGivenException;

@ControllerAdvice
public class CustomerExceptionHandling {

	@ExceptionHandler(value = { CustomerNotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	protected ResponseEntity<String> notFound(ICustomException ex) throws Exception {
		if (ex instanceof CustomerNotFoundException) {
			return new ResponseEntity<String>(ex.toJson().toString(), HttpStatus.NOT_FOUND);
		}
		throw new Exception();

	}

	@ExceptionHandler(value = { IdentityNotGivenException.class })
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ResponseBody
	protected ResponseEntity<String> identityNotGiven(ICustomException ex) throws Exception {
		if (ex instanceof IdentityNotGivenException)
			return new ResponseEntity<String>(ex.toJson().toString(), HttpStatus.NO_CONTENT);
		throw new Exception();

	}
}
