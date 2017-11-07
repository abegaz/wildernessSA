package application;

import java.util.ArrayList;
import java.sql.*;

public class DBInterface {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		createTable();

	}

	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://24.196.52.155:3306/databaseName"; // alter to reflect actual IP address, port number, and dbname
			String username = ""; 										//add actual username
			String password = ""; 										//add actual password
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected!");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// template for creates
	public static void createTable() throws Exception {
		try {
			Connection conn = getConnection();
			PreparedStatement create = conn.prepareStatement(
					"CREATE TABLE IF NOT EXISTS tablename(id int NOT NULL AUTO_INCREMENT, first varchar(255)");
			create.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Table Created");
		}
	}

	// template for inserts
	public static void insert() throws Exception {
		final String var1 = "";
		final String var2 = "";
		try {
			Connection conn = getConnection();
			PreparedStatement insert = conn
					.prepareStatement("INSERT INTO tableName (col1, col2) VALUES ('" + var1 + "', '" + var2 + "')");
			insert.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Insert completed");
		}
	}

	public static void insertNewReport(Report r) throws Exception {
		final String reportID = Integer.toString(r.reportID);
		final String stationID = Integer.toString(r.stationID);
		final String timestamp = Integer.toString(r.timestamp);
		final String temp = Integer.toString(r.temp);
		final String pressure = Double.toString(r.pressure);
		final String rain = Double.toString(r.rain);
		final String windSpeed = Integer.toString(r.windSpeed);
		final String windDirection = Integer.toString(r.windDirection);
		final String conditions = r.conditions;
		try {
			Connection conn = getConnection();
			PreparedStatement insert = conn.prepareStatement(
					"INSERT INTO reports (reportID, stationID, timestamp, temp, pressure, rain, windSpeed, windDirection, conditions) VALUES ('" + reportID
							+ "', '" + stationID + "', '" + timestamp + "', '" + temp + "', '" + pressure + "', '" + rain + "', '" + windSpeed + "', '"
							+ windDirection + "', '" + conditions + "')");
			insert.executeUpdate();
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
							+ stationID + "', '" + stationName + "', '" + latitude + "', '" + longitude + "')");
			insert.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("New Station Inserted");
		}
	}

	public static void insertNewUser(User u) throws Exception {
		final String userID = u.userID;
		final String username = u.username;
		final String password = u.password;
		final String privilege = u.privelige;
		try {
			Connection conn = getConnection();
			PreparedStatement insert = conn
					.prepareStatement("INSERT INTO users (userID, privilege, username, password) VALUES ('" + userID
							+ "', '" + privilege + "', '" + username + "', '" + password + "')");
			insert.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("New User Inserted");
		}
	}

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
					.prepareStatement("SELECT * FROM reports r, stations s WHERE r.stationID = "
							+ s.stationID + " LIMIT 10");
			ResultSet result = select.executeQuery();
			ArrayList<String> array = new ArrayList<String>();
			while (result.next()) {
				System.out.print(result.getString("reportID"));
				System.out.print(", ");
				System.out.println(result.getString("timestamp"));
				array.add(result.getString("reportID"));
			}
			System.out.println("Select Complete.");
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<String> selectFilteredReports(String tempCompare, String tempValue, String presCompare, String presValue,String rainCompare, String rainValue,String windCompare, String windValue,) throws Exception {
		try {
			Connection conn = getConnection();
			String compareString = "SELECT * FROM reports r, stations s WHERE r.stationID > 0 ";
			if (tempCompare != ""){
				compareString += " AND  temperature " + tempCompare + " " + tempValue;
			}if (presCompare != ""){
				compareString += " AND  pressure " + presCompare + " " + presValue;
			}if (rainCompare != ""){
				compareString += " AND  rain " + rainCompare + " " + rainValue;
			}if (windCompare != ""){
				compareString += " AND  windSpeed " + windCompare + " " + windValue;
			}
			compareString += " LIMIT 50";
			PreparedStatement select = conn.prepareStatement(compareString);
			ResultSet result = select.executeQuery();
			ArrayList<String> array = new ArrayList<String>();
			while (result.next()) {
				System.out.print(result.getString("reportID"));
				System.out.print(", ");
				System.out.println(result.getString("timestamp"));
				array.add(result.getString("reportID"));
			}
			System.out.println("Select Complete.");
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("User deleted");
		}
	}
	
	
}
