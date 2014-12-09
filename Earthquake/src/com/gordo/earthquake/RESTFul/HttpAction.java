/*
 * This class is where the heavy lifting for 
 * HTTP integration is done. It is run on it's own
 * thread and will bubble up the result to the UI\delegate via
 * the interface AsyncHttpResponse
 */
package com.gordo.earthquake.RESTFul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.gordo.earthquake.data.Response;

import android.os.AsyncTask;
import android.util.Log;

public class HttpAction {
	private AsyncHttpResponse _delegate = null;

	public HttpAction(AsyncHttpResponse Delegate) {
		_delegate = Delegate;
	}

	public static Response getHttpResponse(String url) {
		
		// This method is responsible for making the call
		// to the provided URL and then parse the result
		// in to our generic response object
		
		InputStream inputStream = null;
		String result = "";
		int resultCode = 0;
		
		try {

			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
			resultCode = httpResponse.getStatusLine()
					.getStatusCode();
			
			if (resultCode != 200) {
				// 200 = OK
				// Anything else is not good so we will return the 
				// result as an error
				Log.d("HttpAction", httpResponse.getStatusLine()
						.getReasonPhrase());
				return new Response(true, httpResponse.getStatusLine()
						.getReasonPhrase(), String.valueOf(resultCode),
						inputStream == null ? "" : getInputStreamAsString(inputStream));
			}
			inputStream = httpResponse.getEntity().getContent();

			if (inputStream != null) {
				result = getInputStreamAsString(inputStream);
			}

		} catch (Exception ex) {
			// total catastrophic failure with the call, or the data returned
			Log.d("HttpAction", ex.getLocalizedMessage());
			return new Response(true, ex.getLocalizedMessage(), "0", result);
		}

		return new Response(false, "", String.valueOf(resultCode), result);
	}

	private static String getInputStreamAsString(InputStream inputStream)
			throws IOException {

		// Convert the inputStream into a usable String
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String readLine = "";
		StringBuilder resultSet = new StringBuilder();

		while ((readLine = bufferedReader.readLine()) != null)
			resultSet.append(readLine);

		inputStream.close();
		return resultSet.toString();
	}

	public class HttpGetResponse extends AsyncTask<String, Void, Response> {

		// used to take the UI thread request and put on it's own
		// thread to avoid blocks etc.
		@Override
		protected Response doInBackground(String... urls) {
			return getHttpResponse(urls[0]);
		}

		// take the result of the call to get data and push
		// back to the delegate that made the request via
		// our interface method
		@Override
		protected void onPostExecute(Response result) {
			_delegate.processHttpActionResult(result);
		}
	}
}
