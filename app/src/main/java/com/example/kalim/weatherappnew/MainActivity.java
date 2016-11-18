package com.example.kalim.weatherappnew;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kalim.weatherappnew.model.WeatherForecast;
import org.json.JSONException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    TextView textDate,textMin,textMax,weatherType,currentTemp;
    ImageView weatherImage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    int noofdays=7;
    Boolean isMetric=true;
    CoordinatorLayout coordinatorLayout;
    JSONForecastWeatherTask task;
    private List<WeatherUi> weatherList;
    public String cityName;
    int resutlCode=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        updateUi();



//        initializeData();
//        initializeAdapter();


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle("City");



        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity
           Intent intent=new Intent(MainActivity.this,AddCity.class);
            startActivityForResult(intent,resutlCode);

            Toast.makeText(MainActivity.this, "First Run", Toast.LENGTH_LONG)
                    .show();
        }


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();




    }

    private void updateUi() {
        textDate= (TextView) findViewById(R.id.item_date_textview);
        currentTemp= (TextView) findViewById(R.id.item_current_temp_textview);
        textMin= (TextView) findViewById(R.id.item_low_textview_textview);
        textMax= (TextView) findViewById(R.id.item_high_textview);
        weatherType= (TextView) findViewById(R.id.forecast_textview);
        weatherImage= (ImageView) findViewById(R.id.weather_icon);
        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

    }

    private void initializeData() {
        weatherList = new ArrayList<>();
        weatherList.add(new WeatherUi("Tomorrow","clear","19","7",R.drawable.icon_little_day));
        weatherList.add(new WeatherUi("Sunday","clear","19","7",R.drawable.icon_little_day));
        weatherList.add(new WeatherUi("Monday","clear","19","7",R.drawable.icon_little_day));
        weatherList.add(new WeatherUi("Tuesday","clear","19","7",R.drawable.icon_little_day));
        weatherList.add(new WeatherUi("Wednesday","clear","19","7",R.drawable.icon_little_day));
//        weatherList.add(new WeatherUi("Tomorrow","clear","19","7",R.drawable.icon_little_day));
//        weatherList.add(new WeatherUi("Tomorrow","clear","19","7",R.drawable.icon_little_day));
//        weatherList.add(new WeatherUi("Tomorrow","clear","19","7",R.drawable.icon_little_day));
//        weatherList.add(new WeatherUi("Tomorrow","clear","19","7",R.drawable.icon_little_day));
//        weatherList.add(new WeatherUi("Tomorrow","clear","19","7",R.drawable.icon_little_day));

    }



    private void initializeAdapter() {
        RVAdapter adapter = new RVAdapter(weatherList);
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    public void shareForcast(View view) {
        Toast.makeText(MainActivity.this, "Forcast shared", Toast.LENGTH_SHORT).show();
    }

    public void addCity(View view) {
        Intent intent=new Intent(MainActivity.this,AddCity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent,resutlCode);
    }


    public class JSONForecastWeatherTask extends AsyncTask<String, Void, WeatherForecast> {

        @Override
        protected WeatherForecast doInBackground(String... params) {

            String data = ( (new WeatherHttpClient()).getForecastWeatherData(params[0]));
            WeatherForecast forecast = new WeatherForecast();
            try {
                forecast = JSONWeatherParser.getForecastWeather(data);
                System.out.println("Weather ["+forecast+"]");
                // Let's retrieve the icon
                //weather.iconDataF
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return forecast;

        }

        @Override
        protected void onPostExecute(final WeatherForecast weatherForecast) {
            super.onPostExecute(weatherForecast);
            DateFormat dateInstance = SimpleDateFormat.getDateInstance();
            textDate.setText(dateInstance.format(Calendar.getInstance().getTime()));
            collapsingToolbarLayout.setTitle(cityName);
            final double temprature=Math.round(weatherForecast.getForecast(0).forecastTemp.day-273.15);
            double maxCurrent=Math.round(weatherForecast.getForecast(0).forecastTemp.max-273.15);
            double minCurrent=Math.round(weatherForecast.getForecast(0).forecastTemp.min-273.15);
            final String currentDayCondition=weatherForecast.getForecast(0).weather.currentCondition.getDescr();
            int CurrentweatherId=weatherForecast.getForecast(0).weather.currentCondition.getWeatherId();
            weatherType.setText(currentDayCondition);
            String maxCurrentFormat=Utility.formatTemperature(MainActivity.this,maxCurrent,isMetric);
            String minCurrentFormat=Utility.formatTemperature(MainActivity.this,minCurrent,isMetric);
            String tempratureFormat=Utility.formatTemperature(MainActivity.this,temprature,isMetric);
            textMax.setText(maxCurrentFormat);
            textMin.setText(minCurrentFormat);
            currentTemp.setText(tempratureFormat);
            SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("temprature",tempratureFormat);
            editor.putString("maxCurrentFormat",maxCurrentFormat);
            editor.putString("minCurrentFormat",minCurrentFormat);
            editor.putString("weathercondition",currentDayCondition);
            editor.putInt("id",CurrentweatherId);
            editor.apply();
            weatherImage.setImageResource(Utility.getIconResourceForWeatherConditionForDay(CurrentweatherId));
            weatherList = new ArrayList<>();

          for(int i=0;i<noofdays;i++){
              double maxtemp=Math.round(weatherForecast.getForecast(i).forecastTemp.max-273.15);
              double mintemp=Math.round(weatherForecast.getForecast(i).forecastTemp.min-273.15);
              String condition=weatherForecast.getForecast(i).weather.currentCondition.getCondition();
              String description=weatherForecast.getForecast(i).weather.currentCondition.getDescr();
              String a=""+weatherForecast.getForecast(i).timestamp;
              String b="000";
              String l=a+b;
             long date=Long.parseLong(l,10);
              int weatherId=weatherForecast.getForecast(i).weather.currentCondition.getWeatherId();
              weatherList.add(new WeatherUi(Utility.getDate(date,"EEEE dd"),condition+"("+description+")",Utility.formatTemperature(MainActivity.this,maxtemp,isMetric),Utility.formatTemperature(MainActivity.this,mintemp,isMetric),Utility.getIconResourceForWeatherCondition(weatherId)));
          }
         initializeAdapter();
            rv.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, rv, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                   // position+=1;
                    double tempratureMor=Math.round(weatherForecast.getForecast(position).forecastTemp.morning-273.15);
                    double tempratureday=Math.round(weatherForecast.getForecast(position).forecastTemp.day-273.15);
                    double tempraturenight=Math.round(weatherForecast.getForecast(position).forecastTemp.night-273.15);
                    double tempratureeve=Math.round(weatherForecast.getForecast(position).forecastTemp.eve-273.15);
                    double maxCurrentday=Math.round(weatherForecast.getForecast(position).forecastTemp.max-273.15);
                    double minCurrentday=Math.round(weatherForecast.getForecast(position).forecastTemp.min-273.15);
                    String currentDayConditionday=weatherForecast.getForecast(position).weather.currentCondition.getCondition();
                    float windSpeed=weatherForecast.getForecast(position).weather.wind.getSpeed();
                    float windDirection=weatherForecast.getForecast(position).weather.wind.getDeg();
                    float humidity=weatherForecast.getForecast(position).weather.temperature.getHumadity();
                    float pressure=weatherForecast.getForecast(position).weather.currentCondition.getPressure();
                    int weatherId=weatherForecast.getForecast(position).weather.currentCondition.getWeatherId();
                    String a=""+weatherForecast.getForecast(position).timestamp;
                    String b="000";
                    String l=a+b;
                    long date=Long.parseLong(l,10);
                    String weekDay=Utility.getDate(date,"EEEE");
                    String monthdate=Utility.getDate(date,"MMMM dd");
                    String windFormat=Utility.getFormattedWind(MainActivity.this,windSpeed,windDirection);
                    Intent intent=new Intent(MainActivity.this,DetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("weekday",weekDay);
                    intent.putExtra("monthdate",monthdate);
                    intent.putExtra("tmpday",Utility.formatTemperature(MainActivity.this,tempratureday,isMetric));
                    intent.putExtra("tmpmor",Utility.formatTemperature(MainActivity.this,tempratureMor,isMetric));
                    intent.putExtra("tmpnight",Utility.formatTemperature(MainActivity.this,tempraturenight,isMetric));
                    intent.putExtra("tmpeve",Utility.formatTemperature(MainActivity.this,tempratureeve,isMetric));
                    intent.putExtra("tmpmax",Utility.formatTemperature(MainActivity.this,maxCurrentday,isMetric));
                    intent.putExtra("tmpmin",Utility.formatTemperature(MainActivity.this,minCurrentday,isMetric));
                    intent.putExtra("tmpcon",currentDayConditionday);
                    intent.putExtra("tmpwind",windFormat);
                    intent.putExtra("weatherid",weatherId);
                    intent.putExtra("tmphum",humidity);
                    intent.putExtra("tmppre",pressure);
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            }));


        }









    }
    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(MainActivity.this, "on resume ", Toast.LENGTH_SHORT).show();
        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        cityName = sp.getString("cityName", "New-York");
       if (isConnectingToInternet()){
           task = new JSONForecastWeatherTask();
           task.execute(new String[]{cityName});
       }else{
           String maxtemp=sp.getString("maxCurrentFormat","34");
           String mintemp=sp.getString("minCurrentFormat","12");
           String temprature=sp.getString("temprature","12");
           String condition=sp.getString("weathercondition","clear");
           int id=sp.getInt("id",1);
           weatherType.setText(condition);
           weatherImage.setImageResource(Utility.getIconResourceForWeatherConditionForDay(id));
           currentTemp.setText(temprature);
           textMax.setText(maxtemp);
           textMin.setText(mintemp);
           collapsingToolbarLayout.setTitle(cityName);
           final Snackbar snackbar = Snackbar
                   .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                   .setAction("RETRY", new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if(isConnectingToInternet()){
                               task = new JSONForecastWeatherTask();
                               task.execute(new String[]{cityName});
                           }else{
                               Snackbar.make(view, "No internet conection", Snackbar.LENGTH_LONG)
                                       .setAction("Try again", null).show();
                           }



                       }
                   });

// Changing message text color
           snackbar.setActionTextColor(Color.RED);

// Changing action button text color

    snackbar.show();

       }


setupSettings();


    }
    public void refreshUi(MenuItem item) {
if(isConnectingToInternet()){
    task = new JSONForecastWeatherTask();
    task.execute(new String[]{cityName});
}else{
    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
}
    }
    private void setupSettings() {
        SharedPreferences setting= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String tempType=setting.getString(getString(R.string.pref_units_key),getString(R.string.pref_units_metric));
        if(tempType.contains(getString(R.string.pref_units_metric))){
            isMetric=true;
        }else {
            isMetric=false;
        }
        Log.d("tmptype", tempType);
        Log.d("isMetric", String.valueOf(isMetric));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent=new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2){
            try{
                cityName=data.getStringExtra("CITY");
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }
}
