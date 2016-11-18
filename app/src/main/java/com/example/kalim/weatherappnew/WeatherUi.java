package com.example.kalim.weatherappnew;

/**
 * Created by Kalim on 4/7/2016.
 */
public class WeatherUi {
    String dayName;
    String weather_description;
    String maxTemp;
    String minTemp;
    int photoId;

    WeatherUi(String name, String weather_des, String maxt, String mint , int photoId) {
        this.dayName = name;
        this.weather_description=weather_des;
        this.maxTemp=maxt;
        this.minTemp=mint;
        this.photoId = photoId;
    }
}

