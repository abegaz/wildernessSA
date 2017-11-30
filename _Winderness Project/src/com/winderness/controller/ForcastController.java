package com.winderness.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Random;

//Created by Kyle Remillard
public class ForcastController {
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

    Random random = new Random();

    /////////      Methods Below  /////////////////
    public void initialize() {
        generateForcast();
    }
    private void generateForcast(){


        sundayImage.setImage(imageArray[randomImageIndex()]);
        sundayHigh.setText(String.valueOf(getHigh()));
        sundayLow.setText(String.valueOf(getLow()));

        mondayImage.setImage(imageArray[randomImageIndex()]);
        mondayHigh.setText(String.valueOf(getHigh()));
        mondayLow.setText(String.valueOf(getLow()));

        tuesdayImage.setImage(imageArray[randomImageIndex()]);
        tuesdayHigh.setText(String.valueOf(getHigh()));
        tuesdayLow.setText(String.valueOf(getLow()));

        wednesdayImage.setImage(imageArray[randomImageIndex()]);
        wednesdayHigh.setText(String.valueOf(getHigh()));
        wednesdayLow.setText(String.valueOf(getLow()));

        thursdayImage.setImage(imageArray[randomImageIndex()]);
        thursdayHigh.setText(String.valueOf(getHigh()));
        thursdayLow.setText(String.valueOf(getLow()));

        fridayImage.setImage(imageArray[randomImageIndex()]);
        fridayHigh.setText(String.valueOf(getHigh()));
        fridayLow.setText(String.valueOf(getLow()));

        saturdayImage.setImage(imageArray[randomImageIndex()]);
        saturdayHigh.setText(String.valueOf(getHigh()));
        saturdayLow.setText(String.valueOf(getLow()));
    }
    private int getHigh(){
        this.high = random.nextInt(30)+50;
        return high;
    }
    private int getLow(){
        this.low = random.nextInt(10)+35;
        return low;
    }
    private int randomImageIndex(){
        int imageIndex = random.nextInt(4)+0;
        return imageIndex;
    }
    public void back(ActionEvent event) throws Exception {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("../view/ReportDetailView.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
    }
}
