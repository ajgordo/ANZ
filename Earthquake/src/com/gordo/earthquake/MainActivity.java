package com.gordo.earthquake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gordo.earthquake.data.Database;
import com.gordo.earthquake.data.Earthquake;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity {

	Database database;
	ExpandableListView expandableListView;
	EarthquakeExpandableListAdapter expandableListAdapter;
	ArrayList<String> listHeaderData;
	HashMap<String, List<Earthquake>> listDetailsData;
	RadioGroup radioGroupBy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		database = new Database(this);

		expandableListView = (ExpandableListView) findViewById(R.id.expandable_earthquakes);
		radioGroupBy = (RadioGroup) findViewById(R.id.radioGroupBy);
		
		// Add a listener on the radio buttons that will populate the listview accordingly
		radioGroupBy.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			   
			   @Override
			   public void onCheckedChanged(RadioGroup arg0, int arg1) {
			    int id=arg0.getCheckedRadioButtonId();
			    buildListData(id == R.id.radioMagnitude ? "magnitude" : "region");			   }
			  });
		
		// Add a listener that will show more data when you click on one of the listed earthquakes
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
        	 
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
            	
            	Earthquake eq = listDetailsData.get(listHeaderData.get(groupPosition)).get(childPosition);
            	Toast.makeText(getApplicationContext(), 
                              getString(R.string.eq_det_lat_label) + " " + 
            	              String.valueOf(eq.getLat()) + "\r\n" +
                              getString(R.string.eq_det_lon_label) + " " + 
             	              String.valueOf(eq.getLon()) + "\r\n" +
                              getString(R.string.eq_det_eqid_label) + " " + 
                              eq.getEqid() + "\r\n" +
                			  getString(R.string.eq_det_src_label) + " " + 
                			  eq.getSrc(),
                			  Toast.LENGTH_SHORT).show();
            
                return false;

			}
        });
 
	}
	
	private void buildListData(String groupColumn) {
		// Build the Data that is to be displayed in the list
		listHeaderData = new ArrayList<String>();
		listDetailsData = new HashMap<String, List<Earthquake>>();

		listHeaderData = database.fetchDistinctStringList("earthquake",
				groupColumn, true);

		for (int i = 0; i < listHeaderData.size(); i++) {
			List<Earthquake> listEarthquakes = database.fetchEarthquakeData(
					groupColumn + " = '" + Common.escapeQuery(listHeaderData.get(i)) + "'",
					"timedate");

			listDetailsData.put(listHeaderData.get(i), listEarthquakes);
		}
		
		expandableListAdapter = new EarthquakeExpandableListAdapter(this,
				listHeaderData, listDetailsData);
		expandableListView.setAdapter(expandableListAdapter);
		expandableListAdapter.notifyDataSetChanged();
	}
}
