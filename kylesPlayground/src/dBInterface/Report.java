package dBInterface;

public class Report {

    public static int fromStation;
    public static int reportID;
    public static int timeCreated;
    public static int temperature;
    public static double pressure;
    public static String conditions;
    public static double rain;
    public static int windSpeed;
    public static int windDirection;

    public Report(int i){
        this.fromStation = i;
        this.reportID = 0;
        this.timeCreated = 0;
        this.temperature = 0;
        this.pressure = 0;
        this.conditions = null;
        this.rain = 0;
        this.windSpeed = 0;
        this.windDirection = 0;
    }



    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}


