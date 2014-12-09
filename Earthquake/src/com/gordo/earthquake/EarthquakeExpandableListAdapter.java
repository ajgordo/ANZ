package com.gordo.earthquake;

import java.util.HashMap;
import java.util.List;

import com.gordo.earthquake.data.Earthquake;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams") 
public class EarthquakeExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listHeaderData;
	private HashMap<String, List<Earthquake>> _listDetailData;

	public EarthquakeExpandableListAdapter(Context context,
			List<String> listHeaderData,
			HashMap<String, List<Earthquake>> listDetailData) {
		this._context = context;
		this._listHeaderData = listHeaderData;
		this._listDetailData = listDetailData;
	}

	@Override
	public int getGroupCount() {
		return this._listHeaderData.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDetailData
				.get(this._listHeaderData.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listHeaderData.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this._listDetailData
				.get(this._listHeaderData.get(groupPosition))
				.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.layout_earthquake_list_header, null);
		}

		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final String magnitudeText = String.valueOf(((Earthquake) getChild(
				groupPosition, childPosition)).getMagnitude());
		final String depthText = String.valueOf(((Earthquake) getChild(
				groupPosition, childPosition)).getDepth());
		final String datetimeText = ((Earthquake) getChild(groupPosition,
				childPosition)).getTimedate();
		final String regionText = ((Earthquake) getChild(groupPosition,
				childPosition)).getRegion();

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.layout_earthquake_list_detail, null);
		}

		((TextView) convertView.findViewById(R.id.lblDate))
				.setText(datetimeText);
		((TextView) convertView.findViewById(R.id.lblMagnitude))
				.setText(magnitudeText);
		((TextView) convertView.findViewById(R.id.lblDepth)).setText(depthText);
		((TextView) convertView.findViewById(R.id.lblRegion))
				.setText(regionText);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
