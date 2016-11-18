package com.example.kalim.weatherappnew;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kalim on 2/1/2016.
 */
public class CardViewActivity extends Activity {

    TextView dayName,weather_description,minTemp,maxTemp;
    ImageView weatherPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.card_view);
        dayName = (TextView)findViewById(R.id.weather_day);
        weather_description= (TextView) findViewById(R.id.weather_description);
        maxTemp= (TextView) findViewById(R.id.max_temp);
        minTemp= (TextView) findViewById(R.id.min_temp);
        weatherPhoto = (ImageView)findViewById(R.id.weather_photo);
        weatherPhoto.setImageResource(R.drawable.icon_little_day);
    }
}
