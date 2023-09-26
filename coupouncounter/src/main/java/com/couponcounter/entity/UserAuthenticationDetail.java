package com.couponcounter.entity;

import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_Authentication_details")
@Component
public class UserAuthenticationDetail {

	@Id
	private String email;
	private String accessToken;
	private Date expiryTime;
	
	public UserAuthenticationDetail() {
		
	}

	public UserAuthenticationDetail(String email, String accessToken, Date expiryTime) {
		this.email = email;
		this.accessToken = accessToken;
		this.expiryTime = expiryTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Date getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	@Override
	public String toString() {
		return "UserAuthenticationDetail [email=" + email + ", accessToken=" + accessToken + ", expiryTime="
				+ expiryTime + "]";
	}

	
}
