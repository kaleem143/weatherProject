package com.example.kalim.weatherappnew.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kalim on 4/21/2016.
 */
public class WeatherForecast {

    private List<DayForecast> daysForecast = new ArrayList<DayForecast>();

    public void addForecast(DayForecast forecast) {
        daysForecast.add(forecast);
        System.out.println("Add forecast ["+forecast+"]");
    }

    public DayForecast getForecast(int dayNum) {
        return daysForecast.get(dayNum);
    }
}
