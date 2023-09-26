package com.couponcounter.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Component
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "coupon_id")
	private Integer id;

	@Column(name = "coupon_no", unique = true)
	private String couponNo;

	@Column(name = "discount")
	private int discount;

	@Column(name = "type")
	private String type;

	@Column(name = "validity")
	private LocalDate validity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	private Users createdBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by")
	private Users updatedBy;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "Coupon_Valid")
	private boolean couponValid;

	public Coupon() {
		
	}

	public Coupon(String couponNo, int discount, String type, LocalDate validity,Users createdBy,boolean couponValid) {
		this.couponNo = couponNo;
		this.discount = discount;
		this.type = type;
		this.validity = validity;
		this.createdAt = java.time.LocalDateTime.now();
		this.createdBy = createdBy;
		this.couponValid =couponValid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDate getValidity() {
		return validity;
	}

	public void setValidity(LocalDate validity) {
		this.validity = validity;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Users getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Users createdBy) {
		this.createdBy = createdBy;
	}

	public Users getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Users updatedBy) {
		this.updatedBy = updatedBy;
	}

	public boolean isCouponValid() {
		return couponValid;
	}

	public void setCouponValid(boolean couponValid) {
		this.couponValid = couponValid;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean getCouponValidity() {
		return couponValid;
	}

	public void setCouponValidity(boolean couponValid) {
		this.couponValid = couponValid;
	}

	
}
