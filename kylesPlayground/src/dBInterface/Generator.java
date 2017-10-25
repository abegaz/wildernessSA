package dBInterface;

import java.util.ArrayList;
import java.util.Random;
/*
Author: Kyle Remillard
Created 10/25/2017
 */
public class Generator {

    ArrayList<String> stations = new ArrayList<>();
    ArrayList<String> conditions = new ArrayList<>();
    ArrayList<String> pressure = new ArrayList<>();
    String barometricPressure;
    String stringConditions;
    String stringRain;
    String stringReportID;
    String stringTemperature;
    String stringYear;
    String stringMonth;
    String stringDay;
    String stringDateTime;
    String stringWindDirection;
    String stringWindSpeed;
    Double doubleRandomPressure;
    int intRandomPressure;
    int intConditions;
    int intRain;
    int intReportID; //TODO make a query to get highest already in DB
    int intTemperature;
    int intTemperature1;
    int intYear;
    int intMonth;
    int intDay;
    int intWindDirection;
    int intWindSpeed;
    Random random = new Random();
 /**************** Declarations above ************************************************/
    private void populateStations() {
        stations.add("acadia");
        stations.add("American Samoa");
        stations.add("Arches");
        stations.add("Badlands");
        stations.add("Big Bend");
        stations.add("Biscayne");
        stations.add("Black canyon of the gunnison");
        stations.add("Bryce Canyon");
        stations.add("Canonlands");
        stations.add("Capitol Reef");
        stations.add("Carlsbad Caverns");
        stations.add("Channel Islands");
        stations.add("Congaree");
        stations.add("Crater Lake");
        stations.add("Cuyahoga Valley");
        stations.add("Death Valley");
        stations.add("Denali");
        stations.add("Dry Tortugas");
        stations.add("Everglades");
        stations.add("Gates of the Arctic");
    }

    private void populateConditions(){
        conditions.add("Sunny");
        conditions.add("Partly Cloudy");
        conditions.add("Cloudy");
        conditions.add("Scattered Showers");
        conditions.add("Rainy");
        conditions.add("Severe Thunder Storms");
    }
    /******************* List initialization above *************************************/
    private void generateReport() {

    }

    private String randomStation() {
        int randStation = random.nextInt(19) + 0;
        String station = stations.get(randStation);
        return station;
    }

    private String generateConditions() {
        intConditions = random.nextInt(5)+1;
        stringConditions = conditions.get(intConditions);
        return stringConditions;
    }

    private String generatePressure(){
        intRandomPressure = random.nextInt(320000)+250000;
        doubleRandomPressure = intRandomPressure/10000.0;
        barometricPressure = doubleRandomPressure.toString();
        return  barometricPressure;
    }

    private String generateRain() {
        intRain = random.nextInt(100)+0;
        stringRain = String.valueOf(intRain);
        return stringRain;
    }
    //have to run getHighestReportID in DBInterface to get this function's input before you can use it
    private String generateReportID(String highestReportID) {
        intReportID = Integer.parseInt(highestReportID);
        intReportID = intReportID + 1;
        stringReportID = String.valueOf(intReportID);
        return stringReportID;
    }

    private String generateTemperature() {
        intTemperature = random.nextInt(110)+0;
        intTemperature1 = random.nextInt(60)+0;
        intTemperature = intTemperature - intTemperature1;
        stringTemperature = String.valueOf(intTemperature);
        return stringTemperature;
    }

    private String generateDateTime(){
        intYear = random.nextInt(2017)+1900;
        intMonth = random.nextInt(12)+1;
        intDay = random.nextInt(30)+1;
        stringYear = String.valueOf(intYear);
        stringMonth = String.valueOf(intMonth);
        stringDay = String.valueOf(intDay);
        stringDateTime = stringYear + "/" + stringMonth + "/" + stringDay;
        return stringDateTime;
    }

    private String generateWindDirection() {
        intWindDirection = random.nextInt(8)+1;
        switch (intWindDirection){
            case 1:
                stringWindDirection = "N";
                break;
            case 2:
                stringWindDirection = "NE";
                break;
            case 3:
                stringWindDirection = "E";
                break;
            case 4:
                stringWindDirection = "SE";
                break;
            case 5:
                stringWindDirection = "S";
                break;
            case 6:
                stringWindDirection = "SW";
                break;
            case 7:
                stringWindDirection = "W";
                break;
            case 8:
                stringWindDirection = "NW";
                break;
        }
        return stringWindDirection;
    }

    private String generateWindSpeed() {
        intWindSpeed = random.nextInt(120)+0;
        stringWindSpeed = String.valueOf(intWindSpeed);
        return stringWindSpeed;
    }
}
