package com.example.kalim.weatherappnew.databasemodule;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	private Context mycontext;

	private String DB_PATH;// = "/data/data/com.sharpsol.digitalvalley.urduDictionary/databases/";
	private static String DB_NAME = DbConstents.dbName;//the extension may be .sqlite or .db
	public SQLiteDatabase myDataBase;

	public DatabaseHelper(Context context) throws IOException  {
		super(context,DB_NAME,null,1);
		this.mycontext=context;
		SQLiteDatabase myDB= this.getReadableDatabase();
		String outFileName =myDB.getPath();
		this.DB_PATH=outFileName;
		boolean dbexist = checkdatabase();
		if(dbexist)
		{
			//System.out.println("Database exists");
			opendatabase(); 
		}
		else
		{
			System.out.println("Database doesn't exist");
			createdatabase();
		}

	}

	public void createdatabase() throws IOException{
		boolean dbexist = checkdatabase();
		if(dbexist)
		{
			//System.out.println(" Database exists.");
		}
		else{
			this.getReadableDatabase();
			try{
				copydatabase();
			}
			catch(IOException e){
				throw new Error("Error copying database");
			}
		}
	}
	private boolean checkdatabase() {
		//SQLiteDatabase checkdb = null;
		boolean checkdb = false;
		try{
			String myPath = DB_PATH + DB_NAME;
			File dbfile = new File(myPath);
			//checkdb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
			checkdb = dbfile.exists();
		}
		catch(SQLiteException e){
			System.out.println("Database doesn't exist");
		}

		return checkdb;
	}
	private void copydatabase() throws IOException {
		AssetManager myAssetManager = null;
		InputStream[] separatedInput = new InputStream[5];	// Number of separated files which will be assembled
		BufferedInputStream[] separatedBuffer = new BufferedInputStream[5];

		FileOutputStream assembledOutput = null;
		BufferedOutputStream assembledBuffer = null;

		try {
			File newlyAssembled = new File(DB_PATH + DB_NAME);
			Log.i("SpanishProjetc", "newlyAssembled: "+newlyAssembled);

			myAssetManager = mycontext.getResources().getAssets();

			for(int i = 0; i < separatedInput.length; i++) {
				separatedInput[i] = myAssetManager.open("part"+(i+1+".db"));
				separatedBuffer[i] = new BufferedInputStream(separatedInput[i]);
			}
			assembledOutput = new FileOutputStream(newlyAssembled);
			assembledBuffer = new BufferedOutputStream(assembledOutput);

			int read = -1;
			byte[] buffer = new byte[1024];

			for(int i = 0; i < separatedInput.length; i++) {
				while((read = separatedBuffer[i].read(buffer, 0, 1024)) != -1) {
					assembledBuffer.write(buffer, 0, read);
				}

				assembledBuffer.flush();

				Log.i("SpanishProjetc", "Wrote InputStream["+i+"] successfully");
			}

			Log.i("SpanishProjetc", "Assembling Succeeded!");
		}
		catch(Exception e) {
			Log.i("SpanishProjetc", "Assembling FAILED...");
		}
		finally {
			for(int i = 0; i < separatedInput.length; i++) {
				try{if(separatedInput[i] != null) separatedInput[i].close();}catch(Exception e){}
				try{if(separatedBuffer[i] != null) separatedBuffer[i].close();}catch(Exception e){}
			}

			try{if(assembledOutput != null) assembledOutput.close();}catch(Exception e){}
			try{if(assembledBuffer != null) assembledBuffer.close();}catch(Exception e){}

			separatedInput = null;
			separatedBuffer = null;
		}

	}

	public void opendatabase() throws SQLException
	{
		//Open the database
		String mypath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);

	}



	public synchronized void close(){
		if(myDataBase != null){
			myDataBase.close();
		}
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
}