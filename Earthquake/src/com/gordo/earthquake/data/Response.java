/*
 * The following is a general response class.
 * It is designed to create a general response mechanism
 * that would allow you to get data from not only RESTful\HTTP,
 * but from anywhere, and the nothing that uses it would need
 * to be changed - it's a point of abstraction
 */
package com.gordo.earthquake.data;

import java.io.Serializable;

public class Response implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean hasError;
	private String errorMessage;
	private String resultCode;
	private String rawResponse;

	public Response(boolean HasError, String ErrorMessage, String ResultCode,
			String RawResponse) {
		this.hasError = HasError;
		this.errorMessage = ErrorMessage;
		this.resultCode = ResultCode;
		this.rawResponse = RawResponse;
	}

	public void setHasError(boolean value) {
		hasError = value;
	}

	public boolean getHasError() {
		return hasError;
	}

	public void setErrorMessage(String value) {
		errorMessage = value;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setResultCode(String value) {
		resultCode = value;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setRawResponse(String value) {
		rawResponse = value;
	}

	public String getRawResponse() {
		return rawResponse;
	}

}
