package com.wildertest.controller;

import application.WilderTestDBConfig;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class LoginController {
    @FXML private Label loginTitleLabel, loginUsernameResultLabel, loginPasswordResultLabel;
    @FXML private TextField usernameTextField, passwordTextField;
    @FXML private Button loginButton;

    private Stage stage;
    private AnchorPane root;
    private Scene scene;
    //private Connection conn; //never used
    private static String permission = "";
    private static String userID = "";
    private int usernameInt;
    private int passwordInt;

    public void login(ActionEvent event) throws Exception {
    	/*String*/ permission ="";
        validatePasswordAndUsername();
    	checkUsersTable(); //for testing
        try {
            permission = getPermission(usernameTextField.getText(), String.valueOf(hashPass(passwordTextField.getText())));

            //conn = WilderTestDBConfig.getConnection();//shouldn't need to connect to the database here

        } catch (Exception e) {
        	e.printStackTrace();
        }
        try {
            if(checkHashPass() == true) {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                if(permission.equals("1"))
                {
                    root = FXMLLoader.load(getClass().getResource("../view/AdminGUI.fxml"));
                    scene = new Scene(root);
                    stage.setScene(scene);
                }
                else if(permission.equals("0")){
                    root = FXMLLoader.load(getClass().getResource("../view/ReportDetailView.fxml"));
                    scene = new Scene(root);
                    stage.setScene(scene);
                }
                else System.out.println("login failed");

            } else System.out.println("login failed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    	

        
        

    }
    public int hashPass(String password) {
        int hash = 7;
        for (int i = 0; i < password.length(); i++) {
            hash = hash*31 + password.charAt(i);
        }
        System.out.println("Password: " + password + ", hash: " + hash);
        return hash;
    }

    public boolean checkHashPass(){
    	//all of this is already taken care of
        //String permission ="";
//        try {
//            permission = getPermission(usernameTextField.getText(), String.valueOf(hashPass(passwordTextField.getText())));
//            System.out.println("The permission level is "+ permission);
//
//            //conn = WilderTestDBConfig.getConnection(); //shouldn't need to connect to the database here
//
//        } catch (Exception e) {
//        	e.printStackTrace();
//        }
        if (permission.equals("1") || permission.equals("0")) {
            return true;
        }
            else return false;
    }
    /*
SELECT users.privilege
FROM users
WHERE users.password = 1449706402
AND
users.username = 'Admin'
GROUP BY users.userID;

 */
    public static String getPermission(String username, String password) {
//    	Statement statement = null;
//    	ResultSet resultSet = null;
//    	Connection connection = null;
        try( //Need to either put these here, or close them all in a finally statement
    		Connection connection = WilderTestDBConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT users.privilege, users.userID FROM users Where users.password ="+password+
            " AND users.username ="+"'"+username+"'"+" GROUP BY users.userID;");
            ResultSet resultSet = statement.executeQuery();
        ){
            while (resultSet.next()) {
                permission = String.valueOf(resultSet.getInt("privilege"));
                userID = String.valueOf(resultSet.getInt("userID"));
            }
            //connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
//        finally { //example of closing them in a finally statement
//        	try { resultSet.close(); } catch (Exception e) { /* ignored */ }
//            try { statement.close(); } catch (Exception e) { /* ignored */ }
//            try { connection.close(); } catch (Exception e) { /* ignored */ }
//        }
        System.out.println("the userID is: "+userID);
        System.out.println("privilege @getPermission is "+ permission);
        return permission;
    }

    private void checkUsersTable()
    {
    	//testing\/
    	//below is for testing to see what is in the database
    	Statement stmt = null;
        ResultSet rs = null;
        Connection conn2 = null;
        ResultSetMetaData rsmd = null;
        try{
        	conn2 = WilderTestDBConfig.getConnection();
        	stmt = conn2.createStatement();
            
	    	rs = stmt.executeQuery("SELECT * FROM users");// "SELECT * FROM reports"
	    	rsmd = rs.getMetaData();
	    	int columnCount = rsmd.getColumnCount();
	
	    	System.out.println(columnCount);
	    	// The column count starts from 1
	    	for (int i = 1; i <= columnCount; i++ ) {
	    	  String name = rsmd.getColumnName(i);
	    	  // Do stuff with name
	    	  System.out.println(name + " | " + rsmd.getColumnLabel(i) + " | " + rsmd.getColumnType(i) + " | " + rsmd.getColumnTypeName(i));
	    	}
	    	while(rs.next())
	    	{
	    		System.out.println(rs.getInt("userID") + " | " + rs.getString("privilege") + " | " + rs.getString("username")+ " | " + rs.getString("password"));
	    	}
        } catch (Exception e) {
            System.out.println("Exception: " + e.toString());
        }
        finally {
        	try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { conn2.close(); } catch (Exception e) { /* ignored */ }
       }
        //^testing^
    }
    public void validatePasswordAndUsername(){
        try {
            Connection conn = WilderTestDBConfig.getConnection();

            PreparedStatement updateUsers = conn.prepareStatement("SELECT COUNT(username) FROM users " +
                    "WHERE username like '" + usernameTextField.getText() + "';");
            ResultSet resultSet = updateUsers.executeQuery();
            while (resultSet.next()) {
                usernameInt = resultSet.getInt("COUNT(username)");
        }
            updateUsers = conn.prepareStatement("SELECT COUNT(`password`) FROM users WHERE " +
                    "`password` like '"+hashPass(passwordTextField.getText())+"'");
            resultSet = updateUsers.executeQuery();
            while(resultSet.next()){
                passwordInt = resultSet.getInt("COUNT(`password`)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(usernameInt <= 0){
            loginUsernameResultLabel.setText("Username");
            loginUsernameResultLabel.setTextFill(Color.RED);
        }
        else
            loginUsernameResultLabel.setText("");
        if(passwordInt <=0) {
            loginPasswordResultLabel.setText("Password");
            loginPasswordResultLabel.setTextFill(Color.RED);
        }
        else
            loginPasswordResultLabel.setText("");
    }
    
}

