package application;

import java.util.Random;

public class Report {
	
	public static int reportID;
	public static int stationID;
	public static int timeStamp;
	public static int temp;
	public static double pressure;
	public static double rain;
	public static int windSpeed;
	public static int windDirection;
	public static String conditions;
	
	public Report(int i){
		this.reportID = i;
		this.stationID = 0;
		this.timeStamp = 0;
		this.temp = 0;
		this.pressure = 0;
		this.rain = 0;
		this.windSpeed = 0;
		this.windDirection = 0;
		this.conditions = "";
	}

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
