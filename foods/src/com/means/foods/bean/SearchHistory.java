package com.means.foods.bean;

import com.j256.ormlite.field.DatabaseField;

public class SearchHistory {
	@DatabaseField(generatedId = true)
	public Integer id;
	@DatabaseField
	public String name;

	public SearchHistory() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
