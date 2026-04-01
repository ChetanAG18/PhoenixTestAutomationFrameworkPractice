package com.demo.csv;

public class UserPojo {
	private String username;
	private String password;

	public UserPojo(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public UserPojo() {
		
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

	@Override
	public String toString() {
		return "UserPojo [username=" + username + ", password=" + password + "]";
	}

}
