/*
 * This is a data object that contains all information 
 * about an earthquake
 * It is used throughout the application where 
 * ever an earthquake is read or written
 */
package com.gordo.earthquake.data;

import java.io.Serializable;

import android.content.ContentValues;

public class Earthquake implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String src;
	private String eqid;
	private String timedate;
	private double lat;
	private double lon;
	private double magnitude;
	private double depth;
	private String region;

	public String getSrc() {
		return src;
	}

	public void setSrc(String value) {
		src = value;
	}

	public String getEqid() {
		return eqid;
	}

	public void setEqid(String value) {
		eqid = value;
	}

	public String getTimedate() {
		return timedate;
	}

	public void setTimedate(String value) {
		timedate = value;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double value) {
		lat = value;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double value) {
		lon = value;
	}

	public double getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(double value) {
		magnitude = value;
	}

	public double getDepth() {
		return depth;
	}

	public void setDepth(double value) {
		depth = value;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String value) {
		region = value;
	}

	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		values.put("src", src);
		values.put("eqid", eqid);
		values.put("timedate", timedate);
		values.put("lat", lat);
		values.put("lon", lon);
		values.put("magnitude", magnitude);
		values.put("depth", depth);
		values.put("region", region);

		return values;
	}

}
