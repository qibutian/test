package com.means.foods.bean;

public class User {

	public static User instance;

	public static User getInstance() {
		if (instance == null) {
			return instance=new User();
		}
		return instance;
	}

	public String uid = "";
	public String token = "";

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
