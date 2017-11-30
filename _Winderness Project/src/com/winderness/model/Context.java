package com.winderness.model;

public class Context {
    private final static Context instance = new Context();
    private User user = new User();
    private Station station = new Station(0);
    
    public static Context getInstance() {
        return instance;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		//this.user = user;
		//this.user.setUserID(user.getUserID());
		this.user.setPrivilege(user.getPrivilege());
		//this.user.setUsername(user.getUsername());
		//this.user.setPassword(user.getPassword());
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		//this.station = station;
		//this.station.setStationID(station.getStationID());
		this.station.setStationName(station.getStationName());
		this.station.setLatitude(station.getLatitude());
		this.station.setLongitude(station.getLongitude());
	}

    
}