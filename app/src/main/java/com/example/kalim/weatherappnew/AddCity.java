package com.example.kalim.weatherappnew;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalim.weatherappnew.databasemodule.DatabaseHelper;
import com.example.kalim.weatherappnew.databasemodule.DbConstents;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AddCity extends AppCompatActivity {
    static ArrayList<String> data=new ArrayList<String>();
    private SQLiteDatabase sqliteDB = null;

    private Toolbar toolbar;
    private String cityName;
    private boolean mSearchCheck;
    private SimpleCursorAdapter mAdapter;
    public static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";
    public static final String CITY_NAME = "cityName";
    private LayoutInflater mInflater;
    private ViewGroup mainContainer;




    private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            mSearchCheck = false;
            return false;
        }

        @Override
        public boolean onQueryTextChange(String query) {
//            if (mSearchCheck) {
            // implement your search here
            giveSuggestions(query);
//            }
            return false;
        }
    };

    private void giveSuggestions(String s) {
        if (s.length() >= 1 ) {
            Cursor cursor = sqliteDB.rawQuery(
                    "SELECT DISTINCT  CITY,COUNTRY_NAME FROM "+s.toString().trim().substring(0,1).toUpperCase()+" WHERE CITY LIKE '"
                            + s.toString().trim().toLowerCase().replace("'", "''")
                            + "%' LIMIT 30", null);
            if (data!=null) {
                data.clear();
            }

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                data = new ArrayList<String>();

                while (!cursor.isAfterLast()) {
                    String object=cursor.getString(0)+"!!!"+cursor.getString(1);
                    data.add(object);
                    cursor.moveToNext();
                }
            }
            final MatrixCursor cursor2 = new MatrixCursor(new String[]{BaseColumns._ID, CITY_NAME});
            for (int i = 0; i < data.size(); i++) {
                cursor2.addRow(new Object[]{i, data.get(i).replace("!!!", ",")});
            }
            final String[] from = new String[]{CITY_NAME};
            final int[] to = new int[]{android.R.id.text1};
            mAdapter = new SimpleCursorAdapter(AddCity.this,
                    android.R.layout.simple_list_item_1,
                    null,
                    from,
                    to,
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            mAdapter.changeCursor(cursor2);
            mSearchView.setSuggestionsAdapter(mAdapter);
        }
    }

    private SearchView.OnSuggestionListener onQuerySuggestion = new SearchView.OnSuggestionListener() {
        @Override
        public boolean onSuggestionSelect(int position) {
            return false;
        }

        @Override
        public boolean onSuggestionClick(int position) {
            findViewById(android.R.id.empty).setVisibility(View.GONE);
            Intent intent=new Intent();

            cityName = data.get(position).replace("!!!",",");
            cityName=cityName.replace(" ","-").trim();
           SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("cityName", cityName);
            editor.apply();
            intent.putExtra("CITY",cityName);
            Log.d("city",cityName);
            setResult(2,intent);
            addItem(position);
            finish();
            return false;
        }
    };

//    private void loadHints() {
//        final String[] from = new String[]{CITY_NAME};
//        final int[] to = new int[]{android.R.id.text1};
//        mAdapter = new SimpleCursorAdapter(getActivity(),
//                R.layout.hint_row,
//                null,
//                from,
//                to,
//                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        toolbar = (Toolbar) findViewById(R.id.tool_bar1); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        try{
            DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

            try {


                dbHelper.createdatabase();
                dbHelper.getWritableDatabase();
                dbHelper.close();


            } catch (IOException e) {

            }

        }catch(Exception e){

        }
        File outFile =getDatabasePath(DbConstents.dbName);
        String outFileName =outFile.getPath()+DbConstents.dbName ;
        sqliteDB = SQLiteDatabase.openOrCreateDatabase(outFileName,
                null);




        mContainerView = (ViewGroup) findViewById(R.id.container);

    }
    private ViewGroup mContainerView;
    private SearchView mSearchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_city, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        mSearchView.setSuggestionsAdapter(mAdapter);
        mSearchView.setOnQueryTextListener(onQuerySearchView);
        mSearchView.setOnSuggestionListener(onQuerySuggestion);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//
        switch (item.getItemId()) {//
//
//
            case R.id.action_search://
                // Hide the "empty" view since there is now at least one item in the list.//
                findViewById(android.R.id.empty).setVisibility(View.GONE);//
                mSearchCheck = true;

                mSearchView.setIconified(false);
                return true;//
        }

        return super.onOptionsItemSelected(item);
    }

    private void addItem(int position) {
        // Instantiate a new "row" view.
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(
                R.layout.list_item_example, mContainerView, false);
//		api.openweathermap.org/data/2.5/forecast/city?id=524901&APPID=1111111111
        // Set the text in the new row to a random country.
        ((TextView) newView.findViewById(android.R.id.text1)).setText(data.get(position).replace("!!!", ","));

        // Set a click listener for the "X" button in the row that will remove the row.
        newView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remove the row from its parent (the container view).
                // Because mContainerView has android:animateLayoutChanges set to true,
                // this removal is automatically animated.
                mContainerView.removeView(newView);

                // If there are no rows remaining, show the empty view.
                if (mContainerView.getChildCount() == 0) {
                    findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
                }
            }
        });

        // Because mContainerView has android:animateLayoutChanges set to true,
        // adding this view is automatically animated.
        mContainerView.addView(newView, 0);
    }

    /**
     * A static list of country names.
     */
    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain",
            "Austria", "Russia", "Poland", "Croatia", "Greece",
            "Ukraine",
    };

    public void addCityByDialogue(View view) {
        new LovelyTextInputDialog(this, R.style.TintTheme)
                .setTopColorRes(R.color.colorAccent)
                .setTitle(R.string.text_input_title)
                .setMessage(R.string.text_input_message)
                .setIcon(R.drawable.art_clouds)

                .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {
                        Toast.makeText(AddCity.this, text, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent();
                        cityName=text.replace(" ","-").trim();
                        intent.putExtra("CITY",cityName);
                        Log.d("city",cityName);
                        setResult(2,intent);
                        finish();


                    }
                })
                .show();
    }
}
