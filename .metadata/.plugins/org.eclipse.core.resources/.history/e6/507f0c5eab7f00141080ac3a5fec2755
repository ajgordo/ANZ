package com.gordo.earthquake.test;

import com.gordo.earthquake.MainActivity;
import com.gordo.earthquake.R;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.RadioGroup;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	MainActivity mainActivity;
	RadioGroup radioGroupBy;
	ExpandableListView expandableListView;
	ListAdapter listAdapter;

	// This is just a simple set of 1 test to ensure it is clear
	// that I can do it.
	public MainActivityTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		mainActivity = getActivity();
		radioGroupBy = (RadioGroup) mainActivity
				.findViewById(R.id.radioGroupBy);
		expandableListView = (ExpandableListView) mainActivity
				.findViewById(R.id.expandable_earthquakes);
	}

	public void testPreConditions() {
		listAdapter = expandableListView.getAdapter();
		assertTrue(listAdapter == null);
	}

	public void testRadioButotns() {
		mainActivity.runOnUiThread(new Runnable() {
			public void run() {
				radioGroupBy.check(R.id.radioMagnitude);
				listAdapter = expandableListView.getAdapter();
				assertTrue(listAdapter != null);
				radioGroupBy.check(R.id.radioRegion);
				listAdapter = expandableListView.getAdapter();
				assertTrue(listAdapter != null);
			}
		});
	}

}
