package com.example.databasemodule;

public class OneCityRecord {

	int id;
	String cityName;
	String cityWeatherID;
	String cityWeatherJSON;

	public OneCityRecord(int id, String cityName, String cityWeatherID, String cityWeatherJSON) {
		super();
		this.id = id;
		this.cityName = cityName;
		this.cityWeatherID = cityWeatherID;
		this.cityWeatherJSON = cityWeatherJSON;
	}
	public OneCityRecord() {
		super();
		this.id =1;
		this.cityName = "London";
		this.cityWeatherID = "1234";
		this.cityWeatherJSON = "json";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityWeatherID() {
		return cityWeatherID;
	}
	public void setCityWeatherID(String cityWeatherID) {
		this.cityWeatherID = cityWeatherID;
	}
	public String getCityWeatherJSON() {
		return cityWeatherJSON;
	}
	public void setCityWeatherJSON(String cityWeatherJSON) {
		this.cityWeatherJSON = cityWeatherJSON;
	}


}
