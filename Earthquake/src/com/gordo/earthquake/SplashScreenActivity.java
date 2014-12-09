package com.gordo.earthquake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.gordo.earthquake.RESTFul.AsyncHttpResponse;
import com.gordo.earthquake.RESTFul.HttpAction;
import com.gordo.earthquake.RESTFul.HttpAction.HttpGetResponse;
import com.gordo.earthquake.data.Database;
import com.gordo.earthquake.data.Earthquake;
import com.gordo.earthquake.data.Response;


public class SplashScreenActivity extends Activity implements AsyncHttpResponse {

	private final String urlToRetreiveData = "http://www.seismi.org/api/eqs/";

	Database database;
	TextView textStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		textStatus = (TextView) findViewById(R.id.splash_load_status);

		database = new Database(this);
		database.getWritableDatabase();

		beginDataLoad();

	}

	private void beginDataLoad() {

		textStatus.setText(getString(R.string.splash_status_details_loading));

		HttpAction httpAction = new HttpAction(this);
		HttpGetResponse httpGetResponse = httpAction.new HttpGetResponse();
		httpGetResponse.execute(urlToRetreiveData);
	}

	@Override
	public void processHttpActionResult(Response result) {

		if (result.getHasError()) {
			// An error has occurred. Check to see if we have
			// previously loaded data and use that if we do
			// rather than just quit, unless we have to
			if (database.getTableRowCount("earthquake") > 0) {
				textStatus.setText(getString(R.string.splash_status_details_no_data_cached));
				// leave the splash screen up for 3 seconds, then progress
				try {
					Thread.sleep(3000);
				} catch (InterruptedException ex) {
					Log.d("Splash HTTP Process", ex.getLocalizedMessage());
					ex.printStackTrace();
				}

			} else {
				// We can't retrieve data, and do't have any
				// so we'll have to close the app for now
				AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

				dlgAlert.setMessage(getString(R.string.splash_status_details_no_data_exit));
				dlgAlert.setTitle(getString(R.string.splash_status_details_no_data_exit_title));
				dlgAlert.setPositiveButton(
						// Close the app
						getString(R.string.splash_status_details_no_data_exit_OK),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(Intent.ACTION_MAIN);
								intent.addCategory(Intent.CATEGORY_HOME);
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent);
							}
						}
				);
				
				dlgAlert.setCancelable(false);
				dlgAlert.create().show();
				database.close();
				return;
			}
		} else {
			try {
				// dump the contents of the table and 
				// re-populate with the latest
				database.cleanEarthquakes();
				JSONObject jsonObj = new JSONObject(result.getRawResponse());
				JSONArray jsonArr = jsonObj.getJSONArray("earthquakes");
				for (int i = 0; i < jsonArr.length(); i++) {
					try {
						Earthquake earthquake = new Earthquake();
						
						earthquake.setSrc(jsonArr.getJSONObject(i).getString(
								"src"));
						earthquake.setEqid(jsonArr.getJSONObject(i).getString(
								"eqid"));
						earthquake.setTimedate(jsonArr.getJSONObject(i)
								.getString("timedate"));
						earthquake.setLat(Double.parseDouble(jsonArr
								.getJSONObject(i).getString("lat")));
						earthquake.setLon(Double.parseDouble(jsonArr
								.getJSONObject(i).getString("lon")));
						earthquake.setMagnitude(Double.parseDouble(jsonArr
								.getJSONObject(i).getString("magnitude")));
						earthquake.setDepth(Double.parseDouble(jsonArr
								.getJSONObject(i).getString("depth")));
						earthquake.setRegion(jsonArr.getJSONObject(i)
								.getString("region"));
						database.insertEarthquake(earthquake);
					} catch (Exception ex) {
						// discard the bad record and move on
						Log.d("Splash HTTP Process", ex.getLocalizedMessage());
					}
				}
			} catch (JSONException ex) {
				Log.d("Splash HTTP Process", ex.getLocalizedMessage());
				ex.printStackTrace();
			}
		}
		database.close();
		Intent intent = new Intent(SplashScreenActivity.this,
				MainActivity.class);
		/*
		 * Bundle earthquakeBundle = new Bundle();
		 * earthquakeBundle.putSerializable("earthquakes", earthquakeArrayList);
		 * intent.putExtras(earthquakeBundle);
		 */
		startActivity(intent);
		finish();

	}

}
