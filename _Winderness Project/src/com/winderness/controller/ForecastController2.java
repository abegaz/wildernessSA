package com.winderness.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import com.winderness.model.Context;
import com.winderness.model.Forecast;
import com.winderness.model.Station;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.eclipsesource.json.*;


public class ForecastController2 {
    @FXML
    private ImageView sundayImage, mondayImage, tuesdayImage, wednesdayImage, thursdayImage, fridayImage, saturdayImage;
    @FXML
    private Label sundayHigh, sundayLow, mondayHigh, mondayLow, tuesdayHigh, tuesdayLow, wednesdayHigh, wednesdayLow,
        thursdayHigh, thursdayLow, fridayHigh, fridayLow, saturdayHigh, saturdayLow;
    @FXML
    private Button goBackButton;

    private Stage stage;
    private AnchorPane root;
    private Scene scene;

    Image cloudy = new Image("images/forcastImages/cloudy.png");
    Image cloudyRainy = new Image("images/forcastImages/cloudyRainy.png");
    Image partlyCloudy = new Image("images/forcastImages/partlyCloudy.png");
    Image sunny = new Image("images/forcastImages/Sunny.png");
    Image sunnyRainy = new Image("images/forcastImages/sunnyRainy.png");
    Image[] imageArray = new Image[] {cloudy, cloudyRainy, partlyCloudy, sunny, sunnyRainy};

    int high, low;

    //Random random = new Random();
    
    //Lists for simpler access to the labels
    private List<ImageView> images;// = Arrays.asList(sundayImage, mondayImage, tuesdayImage, wednesdayImage, thursdayImage, fridayImage, saturdayImage);
    private List<Label> lows;// = Arrays.asList(sundayLow, mondayLow,  tuesdayLow,  wednesdayLow, thursdayLow, fridayLow, saturdayLow);
    private List<Label> highs;// = Arrays.asList(sundayHigh, mondayHigh,  tuesdayHigh,  wednesdayHigh, thursdayHigh, fridayHigh, saturdayHigh);
    private List<Forecast> days = new ArrayList<Forecast>();
    private String timezone = "";
    
    private Station station;
    private JsonObject json = null;
    private JsonArray jarr;

    /////////      Methods Below  /////////////////
    public void initialize() {
    	lows = Arrays.asList(sundayLow, mondayLow,  tuesdayLow,  wednesdayLow, thursdayLow, fridayLow, saturdayLow);
    	highs = Arrays.asList(sundayHigh, mondayHigh,  tuesdayHigh,  wednesdayHigh, thursdayHigh, fridayHigh, saturdayHigh);
    	images = Arrays.asList(sundayImage, mondayImage, tuesdayImage, wednesdayImage, thursdayImage, fridayImage, saturdayImage);

        station = Context.getInstance().getStation();
        testJSON();
        Alert alert;
        if(json == null){
        	alert = new Alert(AlertType.ERROR, "Unable to obtain data!");
    		alert.showAndWait();
        } else {
        	alert = new Alert(AlertType.INFORMATION, "Weekly summary for " + station.getStationName() + ": " + json.getString("summary", "This is where the weekly summary should be."));
        	alert.showAndWait();
        	jarr = json.get("data").asArray();
			System.out.println(jarr.toString());
			
			//int x = 0;
			for (JsonValue item : jarr) {
				if(days.size() == 7)
					break;
				int time = item.asObject().getInt("time", 0);//UNIX time
				int day = getDayFromTime(time);//Day of the week. 0=Sunday, 1=Monday, etc...
				String icon = item.asObject().getString("icon", "clear-day");
				double high = item.asObject().getDouble("temperatureHigh", 0);
				double low = item.asObject().getDouble("temperatureLow", 0);
				System.out.println(item.toString());
				days.add(new Forecast(time, day, icon, high, low));
			}
			if(days.get(0).getDay() != 0)
			{
				int offset = days.get(0).getDay();
				System.out.println(days);
				Collections.rotate(days, offset);
				System.out.println(days);
			}
			for(int i = 0; i < days.size(); i++)
			{
				Forecast day = days.get(i);
				lows.get(i).setText(String.valueOf(day.getLow()));
				highs.get(i).setText(String.valueOf(day.getHigh()));
				//set images
				String icon = day.getIcon();
				if(icon.contains("clear"))
					images.get(i).setImage(sunny);
				else if(icon.contains("partly") || icon.contains("fog"))
					images.get(i).setImage(partlyCloudy);
				else if(icon.contains("cloudy"))
					images.get(i).setImage(cloudy);
				else if(icon.contains("rain") || icon.contains("snow") || icon.contains("sleet"))
					images.get(i).setImage(cloudyRainy);
				else
					images.get(i).setImage(sunnyRainy);
			}
        }
    }
    public void back(ActionEvent event) throws Exception {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("../view/ReportDetailView.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
    }
    
    public void testJSON()
    {
    	URL url;
    	JsonObject json2 = null;
		try {
			String link = " https://api.darksky.net/forecast/a17cbde38ac294f6261f2d08eafddb73/" + 
					station.getLatitude() + "," + station.getLongitude() + "?exclude=currently,minutely,hourly,flags";
			url = new URL(link);//"http://deschr0250.com/json_test3.php");
		    Reader reader = new InputStreamReader(url.openStream());
		    json2 = Json.parse(reader).asObject();
		    System.out.println(json2.toString());

		    timezone = json2.getString("timezone", "Etc/UTC");
			json = json2.get("daily").asObject();
			System.out.println(json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public int getDayFromTime(int timestamp)
    {
    	int day = 0;
    	
    	Instant instant = Instant.ofEpochSecond(timestamp);
    	ZoneId z = ZoneId.of(timezone);
    	ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, z);
    	day = zdt.getDayOfWeek().getValue();
    	System.out.println(instant);
    	System.out.println(zdt);
    	System.out.println(day);
    	System.out.println(zdt.format(DateTimeFormatter.ofPattern("EEEE")));
    	if(day == 7)
    		return 0;
    	else
    		return day;
    }
}
