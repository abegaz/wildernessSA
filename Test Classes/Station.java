package application;

import java.util.Random;

public class Station {

	public static int stationID;
	public static String stationName;
	public static double latitude;
	public static double longitude;
	
	public Station(int i){
		Random random = new Random();
		this.stationID = i;
		this.stationName = "Location #" + Integer.toString(i);
		this.latitude = 90 * random.nextDouble();
	}
	
	public void setStationID(int i){
		this.stationID = i;
	}
	
	public void setStationName(String s){
		this.stationName = s;
	}
	
	public void setLatitude(double d){
		this.latitude = d;
	}
	
	public void setLongitude(double d){
		this.longitude = d;
	}
	
	public Report generateReport(){
		Report report = new Report(stationID);
		return report;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
