package com.means.foods.bean;

public class User {

	public static User instance;

	public static User getInstance() {
		if (instance == null) {
			return new User();
		}
		return instance;
	}

	public String uid = "";

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
