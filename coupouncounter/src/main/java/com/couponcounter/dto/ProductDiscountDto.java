package com.couponcounter.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class ProductDiscountDto {
	@JsonAlias({ "product_id" })
	@JsonProperty("product_id")
	private Integer productId;

	@JsonAlias({ "product_name" })
	@JsonProperty("product_name")
	private String productName;

	@JsonAlias({ "brand" })
	@JsonProperty("brand")
	private String brand;

	@JsonAlias({ "mrp" })
	@JsonProperty("mrp")
	private int mrp;

	@JsonAlias({ "created_at" })
	@JsonProperty("created_at")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private LocalDateTime createdAt;

	@JsonAlias({ "Coupon_id" })
	@JsonProperty("Coupon_id")
	private Integer CouponId;

	@JsonAlias({ "Coupon_no" })
	@JsonProperty("Coupon_no")
	private String CouponNo;

	@JsonAlias({ "discount" })
	@JsonProperty("discount")
	private int discount;

	@JsonAlias({ "type" })
	@JsonProperty("type")
	private String type;

	@JsonAlias({ "validity" })
	@JsonProperty("validity")
	private LocalDate validity;
	
	@JsonAlias({ "discount_value" })
	@JsonProperty("discount_value")
	private int discountValue;
	
	@JsonAlias({ "offered_discount" })
	@JsonProperty("offered_discount")
	private int offeredDiscount;
	
	public ProductDiscountDto() {

	}
	
	public ProductDiscountDto(Integer productId, String productName, String brand, int mrp, LocalDateTime createdAt,
			int i, String CouponNo, int discount, String type, LocalDate validity, int discountValue,
			int offered_discount) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.brand = brand;
		this.mrp = mrp;
		this.createdAt = createdAt;
		this.CouponId = i;
		this.CouponNo = CouponNo;
		this.discount = discount;
		this.type = type;
		this.validity = validity;
		this.discountValue = discountValue;
		this.offeredDiscount = offered_discount;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getCouponId() {
		return CouponId;
	}

	public void setCouponId(Integer CouponId) {
		this.CouponId = CouponId;
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

	public String getCouponNo() {
		return CouponNo;
	}

	public void setCouponNo(String CouponNo) {
		this.CouponNo = CouponNo;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getMrp() {
		return mrp;
	}

	public void setMrp(int mrp) {
		this.mrp = mrp;
	}

	@Override
	public String toString() {
		return "ProductDiscountDto [productId=" + productId + ", productName=" + productName + ", brand=" + brand
				+ ", mrp=" + mrp + ", createdAt=" + createdAt + ", CouponId=" + CouponId + ", CouponNo=" + CouponNo
				+ ", discount=" + discount + ", type=" + type + ", validity=" + validity + ", discountValue="
				+ discountValue + ", offeredDiscount=" + offeredDiscount + "]";
	}

}
