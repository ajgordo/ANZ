package com.gordo.earthquake.data;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

	public Database(Context context) {
		super(context, "EarthquakeDB.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTable = "create table earthquake(src text, eqid text, timedate text, lat REAL, lon REAL, magnitude REAL, depth REAL, region text)";
		db.execSQL(createTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// We have nothing to do here for now
	}

	public void cleanEarthquakes() {
		SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
		sqLiteDatabase.delete("earthquake", null, null);
	}

	public void insertEarthquake(Earthquake earthquake) {
		SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
		sqLiteDatabase
				.insert("earthquake", null, earthquake.getContentValues());
	}

	public ArrayList<Earthquake> fetchEarthquakeData(String whereClause,
			String orderBy) {
		ArrayList<Earthquake> earthquakeArrayList = new ArrayList<Earthquake>();
		String getDataString = "select src, eqid, timedate, lat, lon, magnitude, depth, region from earthquake";
		if (whereClause != null && whereClause.length() > 0) {
			getDataString += (" WHERE " + whereClause);
		}
		if (orderBy != null && orderBy.length() > 0) {
			getDataString += (" ORDER BY " + orderBy);
		}
		SQLiteDatabase eqDatabase = this.getReadableDatabase();
		Cursor cursor = eqDatabase.rawQuery(getDataString, null);
		if (cursor.moveToFirst()) {
			do {
				try {
					Earthquake earthquake = new Earthquake();
					earthquake.setSrc(cursor.getString(0));
					earthquake.setEqid(cursor.getString(1));
					earthquake.setTimedate(cursor.getString(2));
					earthquake.setLat(Double.parseDouble(cursor.getString(3)));
					earthquake.setLon(Double.parseDouble(cursor.getString(4)));
					earthquake.setMagnitude(Double.parseDouble(cursor
							.getString(5)));
					earthquake
							.setDepth(Double.parseDouble(cursor.getString(6)));
					earthquake.setRegion(cursor.getString(7));
					earthquakeArrayList.add(earthquake);
				} catch (Exception ex) {
					// discard and move on
					Log.d("Database", ex.getLocalizedMessage());
				}

			} while (cursor.moveToNext());
		}
		return earthquakeArrayList;
	}

	public ArrayList<String> fetchDistinctStringList(String table,
			String column, boolean orderBy) {
		ArrayList<String> listValues = new ArrayList<String>();
		try {
			String getDataString = "select distinct " + column + " from "
					+ table + (orderBy ? (" order by " + column) : "");
			SQLiteDatabase eqDatabase = this.getReadableDatabase();
			Cursor cursor = eqDatabase.rawQuery(getDataString, null);
			if (cursor.moveToFirst()) {
				do {
					try {
						listValues.add(cursor.getString(0));
					} catch (Exception ex) {
						// discard and move on
						Log.d("Database", ex.getLocalizedMessage());
					}

				} while (cursor.moveToNext());

			}
		} catch (Exception ex) {
			Log.d("Database", ex.getLocalizedMessage());
		}
		return listValues;

	}

	public long getTableRowCount(String table) {
		long rowCount = 0;
		try {
			String getDataString = "select count(*) from " + table;
			SQLiteDatabase eqDatabase = this.getReadableDatabase();
			SQLiteStatement statement = eqDatabase
					.compileStatement(getDataString);
			rowCount = statement.simpleQueryForLong();

		} catch (Exception ex) {
			Log.d("Database", ex.getLocalizedMessage());
		}
		return rowCount;
	}
}
