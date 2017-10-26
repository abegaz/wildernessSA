package dBInterface;

import java.util.ArrayList;
import java.sql.*;
//Author Kyle Rogacki
//Eddited by Kyle Remillard 10/24/2017

/*
    I just changed a bunch of kyles test functions into use able modular components
that we can use to build other stuff.
 */

/*  READ ME !!!!!!!!!!!!
If you get a "java.lang.ClassNotFoundException: com.mysql.jdbc.Driver" error:
Download the jar-bin file from "https://dev.mysql.com/downloads/file/?id=13598".
Then add the jar to your class path for the appropriate module.
    File>ProjectStructure
    ProjectStettings>Library
    Then hit the green "+" symbol in the middle column (or where ever it is in eclipse)
    and select the file from where ever you downloaded and saved it.
    "mysql-connector-java-5.0.8-bin" (the executable file) is what you want to pick.

 */
public class DBInterface {

    public static void main(String[] args) throws Exception { //TODO encorperate these in main driver
        // TODO Auto-generated method stub
//        getConnection(); //Works
//        createTable("testTableName"); //Works
//        insert("testTableName", "id","5"); //Works
//        select1("testTableName", "id", "3");
//        isSame("testTableName", "id", "5");
        int i = 0;
        while(i < 1000) {
            Generator generator = new Generator();
            uploadGenerateReports(generator.generateReport(getHighestReportID()));

            System.out.println(generator.generateReport(getHighestReportID()));
            i++;
        }
    }

    public static Connection getConnection() throws Exception {
        try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://50.62.209.41:3306/wildernessSA"; // alter to reflect actual IP address, port number, and dbname
            String username = "Admin"; 										//add actual username
            String password = "wilderPeople69!"; 										//add actual password
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected!");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//TODO create error messages to check for insertion and deletion
    // template for creates
    public static void createTable(String tableName) throws Exception { // made this take an input for name
        try {
            Connection conn = getConnection();
            PreparedStatement create = conn.prepareStatement( //TODO We should connect only once for many transactions
                    "CREATE TABLE IF NOT EXISTS " + tableName + "(id int NOT NULL AUTO_INCREMENT, PRIMARY KEY(id))");
            create.executeUpdate(); //table has to be created with primary key and int id makes sense for every thing
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connection connection = getConnection();
            PreparedStatement create = connection.prepareStatement(
                    "S"
            );
            //TODO finish creating a check for this
            System.out.println("Table Created");
        }

    }

    // template for inserts
    public static void insert(String tableName, String columnName ,String insertedData) throws Exception {
//        final String var1 = "";
//        final String var2 = "";
        try {
            Connection conn = getConnection(); //made this function take input
            PreparedStatement insert = conn
                    .prepareStatement("INSERT INTO " + tableName + " ("+columnName+") VALUES ('"+insertedData+"')");
            insert.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (isSame(tableName, columnName,insertedData) == true){
                System.out.println("Insert Completed");
            }

            else {
                System.out.print("Insert Failed");
            }
        }
    }

    public static void insertNewReport(Report r) throws Exception {
        final String reportID = Integer.toString(r.reportID);
        final String dateTime = Integer.toString(r.timeCreated);
        final String temp = Integer.toString(r.temperature);
        final String pres = Double.toString(r.pressure);
        final String cond = r.conditions;
        final String rain = Double.toString(r.rain);
        final String wspd = Integer.toString(r.windSpeed);
        final String wdir = Integer.toString(r.windDirection);
        try {
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO reports (reportID, dateTime, temp, pres, cond, rain, wspd, wdir) VALUES ('" + reportID
                            + "', '" + dateTime + "', '" + temp + "', '" + pres + "', '" + cond + "', '" + rain + "', '"
                            + wspd + "', '" + wdir + "')");
            insert.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("New Report Inserted");
        }
    }

    public static void insertNewStation(Station s) throws Exception {
        final String stationID = Integer.toString(s.stationID);
        final String location = s.location;
        final String latitude = Double.toString(s.latitude);
        final String longitude = Double.toString(s.longitude);
        try {
            Connection conn = getConnection();
            PreparedStatement insert = conn
                    .prepareStatement("INSERT INTO stations (stationID, stationName, latitude, longitude) VALUES ('"
                            + stationID + "', '" + location + "', '" + latitude + "', '" + longitude + "')");
            insert.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("New Station Inserted");
        }
    }

    public static void insertNewUser(User u) throws Exception { //They were not wanting to be cast as Strings
        final int userID = u.userID; //changed this to int
        final String username = u.username;
        final String password = u.password;
        final int intPrivilege = u.privilege;
        final int privilege = u.privilege; //changed this to int
        try {
            Connection conn = getConnection();
            PreparedStatement insert = conn
                    .prepareStatement("INSERT INTO users (userID, username, password, privilege) VALUES ('" + userID
                            + "', '" + username + "', '" + password + "', '" + privilege + "')");
            insert.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("New User Inserted");
        }
    }
    /************************************************************************************************/
    // Used in combination with select1 for checking if data was actually entered into the database
    public static boolean isSame(String tableName, String columnName ,String selectedData) {
        String flag = select1(tableName, columnName, selectedData);
        if(selectedData.equals(flag)) {
            return true;
        } else {
            return false;
        }
    }
// Selects a single piece of data from database and returns it as a String
    public static String select1(String tableName, String columnName, String selectedData) {
        String result = "";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT "+columnName+" FROM "+tableName+" WHERE "+columnName+" = "+selectedData);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString(columnName);
            }
            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }
/********************************************************************************************/
    // template for selects
    public static ArrayList<String> select() throws Exception {
        try {
            Connection conn = getConnection();
            PreparedStatement select = conn
                    .prepareStatement("SELECT columnName1, columnName2 FROM tableName WHERE condition");
            ResultSet result = select.executeQuery();
            ArrayList<String> array = new ArrayList<String>();
            while (result.next()) {
                System.out.print(result.getString("columnName1"));
                System.out.print("");
                System.out.print(result.getString("columnName2"));
                array.add(result.getString("columnName2"));
            }
            System.out.println("Select Complete.");
            conn.close();
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> selectTenReportsFromStation(Station s) throws Exception {
        try {
            Connection conn = getConnection();
            PreparedStatement select = conn
                    .prepareStatement("SELECT * FROM stations_has_reports h, reports r, stations s WHERE h.stationID = "
                            + s.stationID + " AND h.reportID = r.reportID LIMIT 10");
            ResultSet result = select.executeQuery();
            ArrayList<String> array = new ArrayList<String>();
            while (result.next()) {
                System.out.print(result.getString("reportID"));
                System.out.print(", ");
                System.out.println(result.getString("dateTime"));
                array.add(result.getString("reportID"));
            }
            System.out.println("Select Complete.");
            conn.close();
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // template for deletes
    public static void delete() throws Exception {
        try {
            Connection conn = getConnection();
            PreparedStatement delete = conn.prepareStatement("DELETE FROM tableName WHERE condition");
            delete.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Deletion completed");
        }
    }

    public static void deleteUser(User u) throws Exception {
        final String username = u.username;
        try {
            Connection conn = getConnection();
            PreparedStatement delete = conn.prepareStatement("DELETE FROM users WHERE username = " + username);
            delete.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("User deleted");
        }
    }
    /*******************************************************************************************/

    public static void uploadGenerateReports(String generatedReport){ //TODO finish this after creating generator
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO reports(stationID, conditions, pressure, rain, reportID, temp," +
                    " `timestamp`, windDirection, windSpeed)" +
                    "VALUES(" + generatedReport + ")");
            preparedStatement.executeUpdate();
            connection.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
    public static String getHighestReportID() {
        String result = "";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT MAX(reportID) FROM reports");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString("MAX(reportID)");
            }
            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

}


