/*
 * The following class is not used, but is used to show
 * an alternate approach to presenting the data in a more 
 * presentable fashion. It was not included for use as it 
 * does not meet the brief as there is a need to make changes
 * to the code - provide an updated developer key for the
 * maps - so therefore is only included as for demonstration.
 * You can call this activity passing in an array list of 
 * earthquake objects and they will be mapped on the map
 * of the world based on lat and lon values
 * 
 */
package com.gordo.earthquake;

import java.util.ArrayList;

import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;
import com.gordo.earthquake.data.Earthquake;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MapActivity extends FragmentActivity {
	private GoogleMap googleMap;
	private ArrayList<Earthquake> earthquakeArrayList = new ArrayList<Earthquake>();

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		earthquakeArrayList = (ArrayList<Earthquake>) getIntent()
				.getSerializableExtra("earthquakes");

		initializeMap();
	}

	private void initializeMap() {

		if (googleMap == null) {

			googleMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (googleMap != null) {
				applyMapContent();
			}
		}
	}

	private void applyMapContent() {
		// Map all points provided as part of the call to this activity
		
		for (int i = 0; i < earthquakeArrayList.size(); i++) {
			Earthquake earthquake = earthquakeArrayList.get(i);
			googleMap.addMarker(new MarkerOptions().position(
									new LatLng(earthquake.getLat(), earthquake.getLon()))
							   .title("Magnatude " + String.valueOf(earthquake.getMagnitude()))
							   .snippet("Date: " + earthquake.getTimedate() + ", " +
									    "Depth: " + String.valueOf(earthquake.getDepth())));
		}
	}

}
