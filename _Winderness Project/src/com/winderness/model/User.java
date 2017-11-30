package com.winderness.model;

public class User {
	private int userID;
	private int privilege;
	private String username;
	private String password;

	public User(int userID, int privilege, String username, String password) {
		super();
		this.userID = userID;
		this.privilege = privilege;
		this.username = username;
		this.password = password;
	}

	public User(String username, String password) {
		super();
		this.userID = 0;
		this.privilege = 0;
		this.username = username;
		this.password = password;
	}

	public User() {
		this.userID = 0;
		this.privilege = 0;
		this.username = "";
		this.password = "";
	}

	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getPrivilege() {
		return privilege;
	}
	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
