package com.winderness.model;

public class Forecast {
	//Object storing the forecast data for 1 day
	private int time;//UNIX time
	private int day;//Day of the week. 0=Sunday, 1=Monday, etc...
	private String icon;
	private double high;
	private double low;
	public Forecast(int time, int day, String icon, double high, double low) {
		this.time = time;
		this.day = day;
		this.icon = icon;
		this.high = high;
		this.low = low;
	}
	public Forecast() {
		this.time = 0;
		this.day = 0;
		this.icon = "";
		this.high = 0;
		this.low = 0;
	}
	@Override
	public String toString() {
		return "Forecast [time=" + time + ", day=" + day + ", icon=" + icon + ", high=" + high + ", low=" + low + "]";
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
}
