package com.easywork.api.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.easywork.api.entity.IEntity;

public abstract class ICustomException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LocalDateTime timeStamp = LocalDateTime.now();
	public HttpStatus status;
	public String[] errors;
	public String message;
	public String url;

	public ICustomException(HttpStatus status, String message, String[] errors, String url) {
		super();
		this.status = status;
		this.errors = errors;
		this.message = message;
		this.url = url;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String[] getErrors() {
		return errors;
	}

	public void setErrors(String[] errors) {
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {

		try {
			return new JSONObject().put("status", this.status.value()).put("message", this.message)
					.put("errors", this.errors).put("url", this.url).put("timestamp", this.timeStamp).toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	public JSONObject toJson() {
		try {
			return new JSONObject().put("status", this.status.value()).put("message", this.message)
					.put("errors", this.errors).put("resource", this.url).put("timestamp", this.timeStamp);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	public void responseException(HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(this.getStatus().value());
		PrintWriter out = response.getWriter();
		out.write(this.toString());
		out.flush();
	}
}
