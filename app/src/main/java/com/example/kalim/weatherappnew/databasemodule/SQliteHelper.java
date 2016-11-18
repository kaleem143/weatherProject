package com.example.kalim.weatherappnew.databasemodule;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQliteHelper extends SQLiteOpenHelper{



	public SQliteHelper(Context context) {
		super(context, DbConstents.dbName, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE "+DbConstents.SavedRecentData+ 
				"(" +DbConstents.colID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+DbConstents.colCityName+" TEXT NOT NULL, "
				+DbConstents.colCityWeatherID+" TEXT NOT NULL, "
				+DbConstents.colCityJSON+" TEXT NOT NULL);");

	}



	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub


		onCreate(db);
	}



}
