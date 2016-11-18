package com.example.kalim.weatherappnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity  {
TextView weekdayText,daytextView,textView_max_temp,textView_min_temp,textView_condition,textView_mor_temp,textView_day_temp,textView_eve_temp,textView_night_temp,textView_humidity,textView_pressure,textView_wind;
    ImageView conditionImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initiliazeUi();
        Intent intent=getIntent();
        String tmpweekday= intent.getExtras().getString("weekday");
        String tmpmonthdate= intent.getExtras().getString("monthdate");
        String tmpday= intent.getExtras().getString("tmpday");
        String tmpnight= intent.getExtras().getString("tmpnight");
        String tmpeve= intent.getExtras().getString("tmpeve");
        String tmpmax= intent.getExtras().getString("tmpmax");
        String tmpmin= intent.getExtras().getString("tmpmin");
        String tmpcon= intent.getExtras().getString("tmpcon");
        String tmpmor= intent.getExtras().getString("tmpmor");
        String tmpwind= intent.getExtras().getString("tmpwind");
        int weatherId=intent.getExtras().getInt("weatherid");
        float tmphum=intent.getExtras().getFloat("tmphum",2);
        float tmppre=intent.getExtras().getFloat("tmppre",2);
        daytextView.setText(tmpmonthdate);
        weekdayText.setText(tmpweekday);
        textView_max_temp.setText(tmpmax);
        textView_min_temp.setText(tmpmin);
        textView_mor_temp.setText(getString(R.string.morning_tmp)+tmpmor);
        textView_day_temp.setText(getString(R.string.day_tmp)+tmpday);
        textView_night_temp.setText(getString(R.string.night_tmp)+tmpnight);
        textView_eve_temp.setText(getString(R.string.evening_temp)+tmpeve);
        textView_condition.setText(tmpcon);
        textView_wind.setText(tmpwind);
        textView_humidity.setText(getString(R.string.format_humidity,tmphum));
        textView_pressure.setText(getString(R.string.format_pressure,tmppre));
        conditionImage.setImageResource(Utility.getIconResourceForWeatherConditionForDay(weatherId));

    }

    private void initiliazeUi() {
        weekdayText= (TextView) findViewById(R.id.week_textView);
        daytextView= (TextView) findViewById(R.id.daytextView);
        textView_max_temp=(TextView)findViewById(R.id.textView_max_temp);
        textView_min_temp=(TextView)findViewById(R.id.textView_min_temp);
        textView_condition=(TextView)findViewById(R.id.textView_condition);
        textView_mor_temp=(TextView)findViewById(R.id.textView_mor_temp);
        textView_day_temp=(TextView)findViewById(R.id.textView_day_temp);
        textView_eve_temp=(TextView)findViewById(R.id.textView_eve_temp);
        textView_night_temp=(TextView)findViewById(R.id.textView_night_temp);
        textView_humidity=(TextView)findViewById(R.id.textView_humidity);
        textView_pressure=(TextView)findViewById(R.id.textView_pressure);
        textView_wind=(TextView)findViewById(R.id.textView_wind);
        conditionImage= (ImageView) findViewById(R.id.imageView_condition);
    }


}
