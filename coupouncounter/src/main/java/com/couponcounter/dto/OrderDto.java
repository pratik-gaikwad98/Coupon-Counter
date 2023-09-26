package com.couponcounter.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class OrderDto {

	@JsonAlias({ "Id" })
	@JsonProperty("id")
	private int id;

	@JsonAlias({ "product_id" })
	@JsonProperty("product_id")
	private Integer product;

	@JsonAlias({ "Coupon_id" })
	@JsonProperty("Coupon_id")
	private Integer Coupon;

	@JsonAlias({ "mrp" })
	@JsonProperty("mrp")
	private int mrp;

	@JsonAlias({ "created_at" })
	@JsonProperty("created_at")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private LocalDateTime createdAt;

	@JsonAlias({ "discount_value" })
	@JsonProperty("discount_value")
	private int discountValue;

	@JsonAlias({ "offered_discount" })
	@JsonProperty("offered_discount")
	private int offeredDiscount;

	@JsonAlias("user_id")
	@JsonProperty("user_id")
	private int userId;

	public OrderDto() {

	}

	public OrderDto(int id, Integer product, Integer Coupon, int mrp, LocalDateTime createdAt, int discountValue,
			int offeredDiscount, int userId) {
		this.id = id;
		this.product = product;
		this.Coupon = Coupon;
		this.mrp = mrp;
		this.createdAt = createdAt;
		this.discountValue = discountValue;
		this.offeredDiscount = offeredDiscount;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Integer getProduct() {
		return product;
	}

	public void setProduct(Integer product) {
		this.product = product;
	}

	public Integer getCoupon() {
		return Coupon;
	}

	public void setCoupon(Integer Coupon) {
		this.Coupon = Coupon;
	}

	public int getMrp() {
		return mrp;
	}

	public void setMrp(int mrp) {
		this.mrp = mrp;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public int getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(int discountValue) {
		this.discountValue = discountValue;
	}

	public int getOfferedDiscount() {
		return offeredDiscount;
	}

	public void setOfferedDiscount(int offeredDiscount) {
		this.offeredDiscount = offeredDiscount;
	}

	@Override
	public String toString() {
		return "OrderDto [id=" + id + ", product=" + product + ", Coupon=" + Coupon + ", mrp=" + mrp + ", createdAt="
				+ createdAt + ", discountValue=" + discountValue + ", offeredDiscount=" + offeredDiscount + "]";
	}
}
