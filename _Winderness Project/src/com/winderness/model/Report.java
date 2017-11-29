package com.winderness.model;

//import javafx.beans.property.SimpleStringProperty;

public class Report {


	private int reportID;
	private int stationID;
    private String timestamp;
    private double temperature;
    private double pressure;
    private double rain;
    private double windSpeed;
    private String/*double*/ windDirection;
    private String conditions;
    //private SimpleStringProperty stationID, reportID,

    public Report(int i){
        this.reportID = 0;
        this.stationID = i;
        this.timestamp = "";
        this.temperature = 0;
        this.pressure = 0;
        this.conditions = null;
        this.rain = 0;
        this.windSpeed = 0;
        this.windDirection = "";//0;
    }
	public Report(int reportID, int stationID, String timestamp, double temperature, double pressure, double rain,
			double windSpeed, String /*double*/ windDirection, String conditions) {
		super();
		this.reportID = reportID;
		this.stationID = stationID;
		this.timestamp = timestamp;
		this.temperature = temperature;
		this.pressure = pressure;
		this.rain = rain;
		this.windSpeed = windSpeed;
		this.windDirection = windDirection;
		this.conditions = conditions;
	}
	public int getReportID() {
		return reportID;
	}
	public void setReportID(int reportID) {
		this.reportID = reportID;
	}
	public int getStationID() {
		return stationID;
	}
	public void setStationID(int stationID) {
		this.stationID = stationID;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timeCreated) {
		this.timestamp = timeCreated;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public double getPressure() {
		return pressure;
	}
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
	public double getRain() {
		return rain;
	}
	public void setRain(double rain) {
		this.rain = rain;
	}
	public double getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}
	public String /*double*/ getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(String /*double*/ windDirection) {
		this.windDirection = windDirection;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

}
