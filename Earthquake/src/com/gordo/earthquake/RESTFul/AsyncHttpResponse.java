/*
 * This is the interface that adds a handler 
 * for getting a response that can be easily
 * consumed by the UI
 */
package com.gordo.earthquake.RESTFul;

import com.gordo.earthquake.data.Response;

public interface AsyncHttpResponse {
	void processHttpActionResult(Response result);
}
