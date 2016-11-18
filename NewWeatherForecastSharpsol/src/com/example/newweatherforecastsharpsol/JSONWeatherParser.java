package com.example.newweatherforecastsharpsol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * Copyright (C) 2013 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class JSONWeatherParser {

	public static Weather getWeather(String data) throws JSONException  {
		Weather weather = new Weather();

		// We create out JSONObject from the data
		JSONObject jObj = new JSONObject(data);

		// We start extracting the info
		Location loc = new Location();
		JSONObject coordObj_city = getObject("city", jObj);

		JSONObject coordObj = getObject("coord", coordObj_city);
		try {
		loc.setLatitude(getFloat("lat", coordObj));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
		loc.setLongitude(getFloat("lon", coordObj));
		} catch (Exception e) {
			// TODO: handle exception
		}
		JSONObject sysObj = getObject("sys", coordObj_city);
		try {
			loc.setCountry(getString("country", coordObj_city));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			loc.setCity(getString("name", coordObj_city));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			loc.setSunrise(getInt("sunrise", sysObj));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			loc.setSunset(getInt("sunset", sysObj));
		} catch (Exception e) {
			// TODO: handle exception
		}


		weather.location = loc;

		// We get weather info (This is an array)
		JSONArray jArr = jObj.getJSONArray("list");

		// We use only the first value
		JSONObject JSONWeather = jArr.getJSONObject(0);
		JSONArray jArr_in_list = JSONWeather.getJSONArray("weather");
		JSONObject JSONWeather_for_3_hr = jArr_in_list.getJSONObject(0);
		try {

			weather.currentCondition.setWeatherId(getInt("id", JSONWeather_for_3_hr));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			weather.currentCondition.setDescr(getString("description", JSONWeather_for_3_hr));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			weather.currentCondition.setCondition(getString("main", JSONWeather_for_3_hr));
		} catch (Exception e) {
			// TODO: handle exception
		}
		//		weather.currentCondition.setIcon(getString("icon", JSONWeather));
		JSONObject JSONWeather_for_Humd_etc = JSONWeather.getJSONObject("main");
		try {
			weather.currentCondition.setHumidity(getInt("humidity", JSONWeather_for_Humd_etc));

		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			weather.currentCondition.setPressure(getInt("pressure", JSONWeather_for_Humd_etc));

		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			weather.temperature.setMaxTemp(getFloat("temp_max", JSONWeather_for_Humd_etc));

		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			weather.temperature.setMinTemp(getFloat("temp_min", JSONWeather_for_Humd_etc));

		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			weather.temperature.setTemp(getFloat("temp", JSONWeather_for_Humd_etc));

		} catch (Exception e) {
			// TODO: handle exception
		}
		weather.temperature.setGroundLevel(getFloat("grnd_level", JSONWeather_for_Humd_etc));
		try {

			weather.temperature.setSeaLevel(getFloat("sea_level", JSONWeather_for_Humd_etc));

		} catch (Exception e) {
			// TODO: handle exception
		}

		// Wind
		try {

			JSONObject JSONWeather_for_wind = JSONWeather.getJSONObject("wind");
			weather.wind.setSpeed(getFloat("speed", JSONWeather_for_wind));
			weather.wind.setDeg(getFloat("deg", JSONWeather_for_wind));

		} catch (Exception e) {
			// TODO: handle exception
		}
		// Clouds
		try {

			JSONObject JSONWeather_for_clouds = JSONWeather.getJSONObject("clouds");
			weather.clouds.setPerc(getInt("all", JSONWeather_for_clouds));

		} catch (Exception e) {
			// TODO: handle exception
		}
		// Rain
		try {

			JSONObject JSONWeather_for_rain = JSONWeather.getJSONObject("rain");
			weather.rain.setAmmount(getFloat("3h", JSONWeather_for_rain));

		} catch (Exception e) {
			// TODO: handle exception
		}
		// Snow
		try {
			JSONObject JSONWeather_for_snow = JSONWeather.getJSONObject("snow");
			weather.snow.setAmmount(getFloat("3h", JSONWeather_for_snow));
		} catch (Exception e) {
			// TODO: handle exception
		}
		// We download the icon to show


		return weather;
	}


	private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
		JSONObject subObj = jObj.getJSONObject(tagName);
		return subObj;
	}

	private static String getString(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getString(tagName);
	}

	private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
		return (float) jObj.getDouble(tagName);
	}

	private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getInt(tagName);
	}

}

