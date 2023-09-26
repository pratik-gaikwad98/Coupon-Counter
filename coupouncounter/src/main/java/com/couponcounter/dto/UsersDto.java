package com.couponcounter.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.couponcounter.entity.Users;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Component
public class UsersDto {
	
	@JsonAlias("user_id")
	@JsonProperty("user_id")
	private int userId;
	
	@JsonAlias("username")
	@JsonProperty("username")
	private String username;
	
	@JsonAlias("password")
	@JsonProperty("password")
	private String password;
	
	@JsonAlias("email")
	@JsonProperty("email")
	private String email;
	
	@JsonAlias("Coupon_id")
	@JsonProperty("Coupon_id")
	private int CouponId;
	
	@JsonAlias("role")
	@JsonProperty("role")
	private List<String> role;
	
		
	public UsersDto() {

	}

	public UsersDto(int userId, String username, String password, String email, int CouponId, List<String> roleId) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.CouponId = CouponId;
		this.role = roleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCouponId() {
		return CouponId;
	}

	public void setCouponId(int CouponId) {
		this.CouponId = CouponId;
	}

	
	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> roleId) {
		this.role = roleId;
	}

	@Override
	public String toString() {
		return "UsersDto [userId=" + userId + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", CouponId=" + CouponId + ", roleId=" + role + "]";
	}
	
}
