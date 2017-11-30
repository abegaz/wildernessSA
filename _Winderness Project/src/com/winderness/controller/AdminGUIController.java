package com.winderness.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import application.WilderTestDBConfig;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AdminGUIController /*extends LoginController*/ {

	@FXML private Button createUserButton, userView, newPasswordButton, logoutButton;;
	@FXML private TextField userNameTextField, passwordTextField, currUserTextField, currPasswordTextField, newPasswordTextField;
	@FXML private Label usernameLabel, passwordLabel, insertStatusLabel;


	private Stage stage;
	private AnchorPane root;
	private Scene scene;
	private Connection conn;

	private int usernameInt;
	private int passwordInt;

	//LoginController loginController = new LoginController(); //hashPass method is static, so an object to access it is not needed

	@FXML
	public void createUser(ActionEvent event) throws Exception{
		try {
			conn = WilderTestDBConfig.getConnection();
			/*
			SELECT COUNT(userID), COUNT(username), COUNT(`password`), COUNT(privilege)
			FROM users
			WHERE username like 'user2' AND `password` like '1991225552';
			*/
			PreparedStatement updateUsers = conn.prepareStatement("SELECT COUNT(username) FROM users " +
					"WHERE username like '" + userNameTextField.getText() + "';");
			ResultSet resultSet = updateUsers.executeQuery();
			while (resultSet.next()) {
				usernameInt = resultSet.getInt("COUNT(username)");
			}
			updateUsers = conn.prepareStatement("SELECT COUNT(`password`) FROM users WHERE " +
					"`password` like '"+ /*loginController*/LoginController.hashPass(passwordTextField.getText())+"'");
			resultSet = updateUsers.executeQuery();
			while(resultSet.next()){
				passwordInt = resultSet.getInt("COUNT(`password`)");
			}
			if(passwordInt > 0){
				System.out.println("password already exists");
				passwordLabel.setText("Password");
			} else
				passwordLabel.setText("");
			if(usernameInt > 0){
				System.out.println("username already exists");
				usernameLabel.setText("Username");
			} else
				usernameLabel.setText("");
			if(passwordInt > 0 || usernameInt > 0){
				insertStatusLabel.setText("Insertion Failed");
				insertStatusLabel.setTextFill(Color.RED);
				return;
			} else if (usernameInt + passwordInt == 0) {
					/*
					This is an example of the propper form and syntax for insertion... gigity
					INSERT INTO users (username, `password`, privilege)
					VALUES('user2', '1991225552', 0);
					*/
				String createUser = "INSERT INTO users (username,  `password`, privilege) VALUES ( '" + userNameTextField.getText() + "', '" + LoginController.hashPass(passwordTextField.getText()) + "', 0)";
				updateUsers = conn.prepareStatement(createUser);
				updateUsers.execute(createUser);
				insertStatusLabel.setText("Insertion Sucessfull");
				insertStatusLabel.setTextFill(Color.GREEN);
			}
			conn.close();//probably should properly close the connection, resultSet, and statement 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void userView(ActionEvent event) throws Exception{
		 stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
         root = FXMLLoader.load(getClass().getResource("../view/ReportDetailView.fxml"));
         scene = new Scene(root);
         stage.setScene(scene);
	}
	@FXML
	public void logout(ActionEvent event) throws Exception{
		 stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
         root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
         scene = new Scene(root);
         stage.setScene(scene);
		
	}
	public void newPassword(ActionEvent event) throws Exception{
		try {
		conn = WilderTestDBConfig.getConnection();
		PreparedStatement updateUser = conn.prepareStatement ("UPDATE users SET password = '"+ LoginController.hashPass(newPasswordTextField.getText()) + "' WHERE users.username like '" + currUserTextField.getText() + "' AND users.password like '" +String.valueOf(LoginController.hashPass(currPasswordTextField.getText()))+"';");
		updateUser.execute();
		conn.close();
		System.out.println("new password created");
		Alert alert = new Alert(AlertType.INFORMATION, "Password updated.");
		alert.showAndWait();

	}
		catch (Exception e) {
			System.out.println("password not created");
			Alert alert = new Alert(AlertType.ERROR, "Password not updated.\n" + e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		}
	}

}
