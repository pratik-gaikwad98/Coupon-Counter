package com.couponcounter.requests;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Sorting {
	
	@JsonAlias("created_at")
	private String createdAt;

	public Sorting() {

	}

	public Sorting(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
