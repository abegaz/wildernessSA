package com.winderness.controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.winderness.model.Report;
import com.winderness.model.Station;

import java.sql.*;

import application.WilderTestDBConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ReportDetailController extends LoginController {
    @FXML private Label titleLabel;
    @FXML private Label reportsLabel;
    @FXML private Label stationLabel;

    @FXML private Pane backgroundPane;

    @FXML private ListView<Station> stationListView;

    @FXML private TableView<Report> reportsTable;
    @FXML private TableColumn<Report, String> dateColumn;
    @FXML private TableColumn<Report, Double> tempColumn;
    @FXML private TableColumn<Report, Double> pressureColumn;
    @FXML private TableColumn<Report, Double> rainColumn;
    @FXML private TableColumn<Report, Double> windSpeedColumn;
    @FXML private TableColumn<Report, String> windDirectionColumn;
    @FXML private TableColumn<Report, String> conditionsColumn;
    @FXML private Button backButton;

    private Stage stage;
    private AnchorPane root;
    private Scene scene;
    
    private static String permission = "";


    @FXML
    public void initialize() {
    	stationListView.setItems(getStationList());
    	
    	
    	dateColumn.setCellValueFactory(new PropertyValueFactory<Report, String>("timeCreated"));
    	tempColumn.setCellValueFactory(new PropertyValueFactory<Report, Double>("temperature"));
    	pressureColumn.setCellValueFactory(new PropertyValueFactory<Report, Double>("pressure"));
        rainColumn.setCellValueFactory(new PropertyValueFactory<Report, Double>("rain"));
        windSpeedColumn.setCellValueFactory(new PropertyValueFactory<Report, Double>("windSpeed"));
        windDirectionColumn.setCellValueFactory(new PropertyValueFactory<Report, String>("windDirection"));
        conditionsColumn.setCellValueFactory(new PropertyValueFactory<Report, String>("conditions"));
        //backButton.setVisible(false); //commented out
        
    	reportsTable.setItems(getReportsList());
    	
    	System.out.println("\n------------------------------------------------------------------\n");
        
    	testDB();
    }
    public void backToAdmin(ActionEvent event) throws Exception {
    			stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            	root = FXMLLoader.load(getClass().getResource("../view/AdminGUI.fxml"));
            	scene = new Scene(root);
            	stage.setScene(scene);
           }
        /*if(permission.equals("1"))
        {
        	backButton.setVisible(true);
        	stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        	root = FXMLLoader.load(getClass().getResource("../view/AdminGUI.fxml"));
        	scene = new Scene(root);
        	stage.setScene(scene);
        }*/

    public boolean visibleButton()
    {
    	permission ="";
    	boolean visible = false;
    	
        try {
             permission = getPermission(usernameTextField.getText(), String.valueOf(hashPass(passwordTextField.getText())));

        } catch (Exception e) {
         	System.out.println("back button not working");
         	e.printStackTrace();
         }
        if(permission.equals("1"))
        {
        	visible = true;
        	backButton.setVisible(visible);
        }
        else
        {
        	backButton.setVisible(visible);
        	backButton.setDisable(visible);
        }
		return visible;
    	
    
    }

    public ObservableList<Station>/*<String>*/  getStationList()
    {
    	ObservableList<Station>/*<String>*/ stations = FXCollections.observableArrayList();

        String SQLQuery = "select stationID, stationName, latitude, longitude from stations";
        
//		ResultSet rs = null;

		try(
				Connection conn = WilderTestDBConfig.getConnection();
				PreparedStatement displayprofile = conn.prepareStatement(SQLQuery);
				ResultSet rs = displayprofile.executeQuery();
		){
			//displayprofile.setInt(1, cutomerId);
//			rs = displayprofile.executeQuery();

			// check to see if receiving any data
			while (rs.next()){
				stations.add(new /*String*/Station(rs.getInt("stationID"), rs.getString("stationName")/*.toString()*/, 
						rs.getDouble("latitude"), rs.getDouble("longitude")));

			}
		}catch(SQLException ex){
			WilderTestDBConfig.displayException(ex);
			return null;
		}/*finally{
			if(rs != null){
				//rs.close();
			}
		}*/
        return stations;
    }
    
    public void mouseClickedOnListView()
    {
        //
    	if(stationListView.getSelectionModel().getSelectedItem() != null)
        {
    		reportsTable.getItems().clear();
    		reportsTable.setItems(getReportsList());
    		//reportsTable.refresh();
        }
    }
    
    public ObservableList<Report>/*<String>*/  getReportsList()
    {
    	ObservableList<Report>/*<String>*/ reports = FXCollections.observableArrayList();

        String SQLQuery = "select reportID, stationID, timestamp, temp, pressure, rain, windSpeed, windDirection, conditions from reports";

        //If something is selected
        if(stationListView.getSelectionModel().getSelectedItem() != null)
        {
        	SQLQuery += " where stationID = " + stationListView.getSelectionModel().getSelectedItem().getStationID();
        	System.out.println(SQLQuery);
        }
        
		//ResultSet rs = null;

		try(
				Connection conn = WilderTestDBConfig.getConnection();
				PreparedStatement displayprofile = conn.prepareStatement(SQLQuery);
				ResultSet rs = displayprofile.executeQuery();
		){
			//displayprofile.setInt(1, cutomerId);
			//rs = displayprofile.executeQuery();

			// check to see if receiving any data
			while (rs.next()){
				reports.add(new Report(rs.getInt("reportID")/*.toString()*/, rs.getInt("stationID")/*.toString()*/, rs.getString("timestamp"),
						rs.getDouble("temp"), rs.getDouble("pressure"), rs.getDouble("rain"),
						rs.getDouble("windSpeed"), rs.getString/*Double*/("windDirection"), rs.getString("conditions")));

			}
		}catch(SQLException ex){
			WilderTestDBConfig.displayException(ex);
			return null;
		}/*finally{
			if(rs != null){
				//rs.close();
			}
		}*/
        return reports;
    }
    
    private void testDB()
    {
    	//below is for testing to see what is in the database
    	Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ResultSetMetaData rsmd = null;
        try{
        	conn = WilderTestDBConfig.getConnection();
        	stmt = conn.createStatement();
	    	rs = stmt.executeQuery("SELECT * FROM stations");// "SELECT * FROM reports"
	    	rsmd = rs.getMetaData();
	    	int columnCount = rsmd.getColumnCount();
	
	    	System.out.println(columnCount);
	    	
	    	for (int i = 1; i <= columnCount; i++ ) { // The column count starts from 1
	    	  String name = rsmd.getColumnName(i);// Do stuff with name
	    	  System.out.println(name + " | " + rsmd.getColumnLabel(i) + " | " + rsmd.getColumnType(i) + " | " + rsmd.getColumnTypeName(i));
	    	}
//	    	rs.first();
//	    	System.out.println(rs.getInt("reportID") + "\t" + rs.getString("timestamp") + "\t" + rs.getDouble("temp"));
//	    	rs.last();
//	    	System.out.println(rs.getInt("reportID") + "\t");
        } catch (Exception e) {
            System.out.println("Exception: " + e.toString());
        }
        finally {
        	try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
       }
    }
}
