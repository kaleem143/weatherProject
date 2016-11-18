package com.example.kalim.weatherappnew;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kalim on 4/15/2016.
 */
public class WeatherHttpClient {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
   // private static String IMG_URL = "http://openweathermap.org/img/w/";


    //private static String BASE_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&id=";





    public String getForecastWeatherData(String location) {
        HttpURLConnection con = null ;
        InputStream is = null;
      //  int forecastDayNum = Integer.parseInt(sForecastDayNum);

        try {
            con = (HttpURLConnection) ( new URL((BASE_URL + location+"&APPID=98e0be9642f5e1807e2b6cb45dff472b"))).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer1 = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br1 = new BufferedReader(new InputStreamReader(is));
            String line1 = null;
            while (  (line1 = br1.readLine()) != null )
                buffer1.append(line1 + "\r\n");

            is.close();
            con.disconnect();

            System.out.println("Buffer ["+buffer1.toString()+"]");
            return buffer1.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }


}
