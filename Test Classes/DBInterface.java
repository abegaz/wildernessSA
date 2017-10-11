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
					.prepareStatement("INSERT INTO users (userID, username, password, privilege) VALUES ('" + userID
							+ "', '" + username + "', '" + password + "', '" + privilege + "')");
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
