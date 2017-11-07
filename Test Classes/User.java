package application;

public class User {
	
	public static int userID;
	public static String username;
	public static String password;
	public static int privilege;
	
	public User(int i){
		this.userID = i;
		this.username = "Kyle";
		this.password = "password";
		this.privilege = 1;
	}
	
	public User newUser(int id, String un, String pw, int lg){
		this.userID = id;
		this.username = un;
		this.password = pw;
		this.privilege = lg;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
