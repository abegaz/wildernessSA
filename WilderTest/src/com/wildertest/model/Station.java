package com.wildertest.model;

public class Station {

	private int stationID;
	private String stationName;
	private double latitude;
	private double longitude;
	
	public Station(int i)
	{
		stationID = i;
		stationName = "";
		latitude = 0;
		longitude = 0;
	}

	public Station(int stationID, String stationName, double latitude, double longitude) {
		//super();
		this.stationID = stationID;
		this.stationName = stationName;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	//needed for the ListView
	public String toString()
	{
		return stationName;
	}

	public int getStationID() {
		return stationID;
	}

	public void setStationID(int stationID) {
		this.stationID = stationID;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
