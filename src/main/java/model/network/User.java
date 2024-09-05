package model.network;

/*
 * Represents a user's credentials
 */
public class User {
	final private String username;
	final private String password;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	/*
	 * The user's username 
	 */
	public String username() { return username; }
	
	/*
	 * The user's password
	 */
	public String password() { return password; }
}
