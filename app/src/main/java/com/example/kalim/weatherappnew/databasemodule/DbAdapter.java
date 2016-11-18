package com.example.kalim.weatherappnew.databasemodule;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DbAdapter {

	SQLiteDatabase mDb;
	Context mCtx;
	SQliteHelper mDbHelper;

	public DbAdapter(Context context){
		this.mCtx = context;
	}

	public DbAdapter open() throws SQLException{
		mDbHelper = new SQliteHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	public boolean isOpen(){
		if (mDb!=null && mDb.isOpen()) {
			return true;
		} else {
			return false;
		}
	}
	public void close()
	{
		mDbHelper.close();
	}

	public long addTask(OneCityRecord record)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(DbConstents.colCityJSON, record.getCityWeatherJSON());
		initialValues.put(DbConstents.colCityName,record.getCityName());
		initialValues.put(DbConstents.colCityWeatherID,record.getCityWeatherID());


		return mDb.insert(DbConstents.SavedRecentData, null, initialValues);
	}


	public int deleteTask(String cityID)
	{
		OneCityRecord temp=new OneCityRecord();
		temp=fetchoneRecordHistory(cityID);
		mDb.delete(DbConstents.SavedRecentData, DbConstents.colID + " = " + cityID+"", null);
		return temp.getId();
	}



	public boolean updateTask(String keyRowIdRecord,OneCityRecord record)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(DbConstents.colCityJSON, record.getCityWeatherJSON());
		initialValues.put(DbConstents.colCityName,record.getCityName());
		initialValues.put(DbConstents.colCityWeatherID,record.getCityWeatherID());

		String where = "_id=?";
		String[] whereArgs = new String[] {String.valueOf(keyRowIdRecord)};
		return mDb.update(DbConstents.SavedRecentData, initialValues, where, whereArgs) > 0;
	}

	public ArrayList<OneCityRecord> fetchAllTasks()
	{
		return courserToList(mDb.query(DbConstents.SavedRecentData, 
				new String[]{
						DbConstents.colID,
						DbConstents.colCityName,
						DbConstents.colCityWeatherID,
						DbConstents.colCityJSON,
		}, null, null, null, null,  DbConstents.colID+" DESC"));
	}


	public OneCityRecord fetchoneRecordHistory(String cityId)
	{
		Cursor c = mDb.query(DbConstents.SavedRecentData, 
				new String[]{
						DbConstents.colID,
						DbConstents.colCityName,
						DbConstents.colCityWeatherID,
						DbConstents.colCityJSON,
		}, DbConstents.colID + " = " + cityId+"", null, null, null, null);
		if(c != null)
		{
			c.moveToFirst();
		}
		return cursorToObject(c);
	}

	public OneCityRecord fetchoneRecord(String cityID)
	{
		Cursor c = mDb.query(DbConstents.SavedRecentData, 
				new String[]{
						DbConstents.colID,
						DbConstents.colCityName,
						DbConstents.colCityWeatherID,
						DbConstents.colCityJSON,
		}, DbConstents.colID + " = " + cityID+"", null, null, null, null);
		if(c != null)
		{
			c.moveToFirst();
		}
		return cursorToObject(c);
	}



	public OneCityRecord cursorToObjectForMainActivity(Cursor mCursor)
	{

		if (!(!(mCursor.moveToFirst()) || mCursor.getCount() ==0)) {
			OneCityRecord aRecord = new OneCityRecord();
			aRecord.setId(mCursor.getInt(0));
			aRecord.setCityName(mCursor.getString(1));
			aRecord.setCityWeatherID(mCursor.getString(2));
			aRecord.setCityWeatherJSON(mCursor.getString(3));
			return aRecord;
		}else {
			return null;
		}

	}
	public OneCityRecord cursorToObject(Cursor mCursor)
	{

		OneCityRecord aRecord = new OneCityRecord();
		aRecord.setId(mCursor.getInt(0));
		aRecord.setCityName(mCursor.getString(1));
		aRecord.setCityWeatherID(mCursor.getString(2));
		aRecord.setCityWeatherJSON(mCursor.getString(3));
		return aRecord;

	}


	ArrayList<OneCityRecord> courserToList(Cursor mCursor){
		ArrayList<OneCityRecord> mArrayList = new ArrayList<OneCityRecord>();
		mCursor.moveToFirst();
		for (int i = 0; i < mCursor.getCount(); i++) {
			mArrayList.add(cursorToObject(mCursor));
			mCursor.moveToNext();
		}
		return mArrayList;
	}

	public boolean valueEcestHistry(String value) {
		// TODO Auto-generated method stub
		OneCityRecord temp=new OneCityRecord();
		temp=fetchoneRecordHistory(value);
		if (temp!=null) {
			return true;
		} else {
			return false;
		}

	}
	public boolean valueEcest(String value) {
		// TODO Auto-generated method stub
		OneCityRecord temp=new OneCityRecord();
		temp=fetchoneRecordActivity(value);
		if (temp!=null) {
			return true;
		} else {
			return false;
		}

	}

	public OneCityRecord fetchoneRecordActivity(String cityID)
	{
		Cursor c = mDb.query(DbConstents.SavedRecentData, 
				new String[]{
						DbConstents.colID,
						DbConstents.colCityName,
						DbConstents.colCityWeatherID,
						DbConstents.colCityJSON,
		}, DbConstents.colID + " = " + cityID+"", null, null, null, null);		
		if(c != null)
		{
			c.moveToFirst();
		}
		return cursorToObjectForMainActivity(c);
	}


}
