package windernessSA;

public class WeatherStation {
	private int windSpeed, airTemp, groundTemp, barometer, rainGauge;
	private String windDirection;
	public WeatherStation(int windSpeed, String windDirection, int airTemp, int groundTemp, int barometer, int rainGauge)
	{
		this.windSpeed = windSpeed;
		this.windDirection = windDirection;
		this. airTemp = airTemp;
		this.groundTemp = groundTemp;
		this.barometer = barometer;
		this.rainGauge = rainGauge;
		
	}
	//------------------------------------------------------------------------------------------------------	
	public void setWindSpeed(int newWindSpeed)
	{
		windSpeed = newWindSpeed;
	}
	public int getWindSpeed()
	{
		return windSpeed;
	}
	public void setWindDirection(String newWindDirection)
	{
		windDirection = newWindDirection;
	}
	public String getWindDirection()
	{
	return windDirection;
	}
	//-----------------------------------------------------------------------------------------------------
	public void setAirT(int newAirT)
	{
		airTemp = newAirT;
	}
	public int getAirT()
	{
	return airTemp;
	}
	public void setGroundT(int newGroundT)
	{
		groundTemp = newGroundT;
	}
	public int getGroundT()
	{
	return groundTemp;
	}
	//-----------------------------------------------------------------------------------------------------
	public void setBarometer(int newBarometer)
	{
	barometer = newBarometer;
	}
	public int getBarometer()
	{
	return barometer;
	}
	//-----------------------------------------------------------------------------------------------------
	public void setRainGauge(int newRainGauge)
	{
	rainGauge = newRainGauge;
	}
	public int getRainGauge()
	{
	return rainGauge;
	}
public String toString()
{
	if(windSpeed >= 253)
	{
	System.out.println("A windspeed of 20.8 is considered strong on the Beaufort scale. 253 is a world record. A systems check for this weather station may be necessary");
	}
	return "Current weather conditions" + "\n"  + "Wind Speed: " + this.windSpeed + "knots" + "\n" + "Wind Direction: " + this.windDirection + "\n" + "Air Temperature: " + this.airTemp + " Celcius" + "\n" + "Ground Temperature: " + this.groundTemp + " Celcius" + "\n" + "Barometric Pressure: " + this.barometer + " atmosphere" + "\n" + "Average Rainfall: " + this.rainGauge + " inches";

}
}
	

