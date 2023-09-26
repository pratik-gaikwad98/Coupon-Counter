package com.couponcounter.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_NULL)
public class FilterFeildsDto {

	@JsonAlias({ "order_id" })
	@JsonProperty("order_id")
	private Integer orderId;
	
	@JsonAlias({ "coupon_id" })
	@JsonProperty("coupon_id")
	private Integer couponId;
	
	@JsonAlias({ "product_id" })
	@JsonProperty("product_id")
	private Integer productId;
	

	@JsonAlias({ "coupon_no" })
	@JsonProperty("coupon_no")
	private String couponNo;

	@JsonAlias({ "discount" })
	@JsonProperty("discount")
	private Integer discount;

	@JsonAlias({ "type" })
	@JsonProperty("type")
	private String type;

	@JsonAlias({ "validity" })
	@JsonProperty("validity")
	private LocalDate validity;

	@JsonProperty("product_name")
	private String productName;

	@JsonProperty("brand")
	private String brand;

	@JsonProperty("mrp")
	private Integer mrp;
	
	@JsonAlias({ "discount_value" })
	@JsonProperty("discount_value")
	private Integer discountValue;
	
	@JsonAlias({ "offered_discount" })
	@JsonProperty("offered_discount")
	private Integer offeredDiscount;
	

	@JsonProperty("created_at")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	@JsonInclude(Include.NON_NULL)
	private LocalDateTime createdAt;


	public FilterFeildsDto() {
	}


	public FilterFeildsDto(Integer orderId, Integer couponId, Integer productId, String couponNo, Integer discount,
			String type, LocalDate validity, String productName, String brand, Integer mrp, Integer discountValue,
			Integer offeredDiscount, LocalDateTime createdAt) {
		this.orderId = orderId;
		this.couponId = couponId;
		this.productId = productId;
		this.couponNo = couponNo;
		this.discount = discount;
		this.type = type;
		this.validity = validity;
		this.productName = productName;
		this.brand = brand;
		this.mrp = mrp;
		this.discountValue = discountValue;
		this.offeredDiscount = offeredDiscount;
		this.createdAt = createdAt;
	}


	public Integer getOrderId() {
		return orderId;
	}


	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}


	public Integer getcouponId() {
		return couponId;
	}


	public void setcouponId(Integer couponId) {
		this.couponId = couponId;
	}


	public Integer getProductId() {
		return productId;
	}


	public void setProductId(Integer productId) {
		this.productId = productId;
	}


	public String getcouponNo() {
		return couponNo;
	}


	public void setcouponNo(String couponNo) {
		this.couponNo = couponNo;
	}


	public Integer getDiscount() {
		return discount;
	}


	public void setDiscount(Integer discount) {
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


	public Integer getMrp() {
		return mrp;
	}


	public void setMrp(Integer mrp) {
		this.mrp = mrp;
	}


	public Integer getDiscountValue() {
		return discountValue;
	}


	public void setDiscountValue(Integer discountValue) {
		this.discountValue = discountValue;
	}


	public Integer getOfferedDiscount() {
		return offeredDiscount;
	}


	public void setOfferedDiscount(Integer offeredDiscount) {
		this.offeredDiscount = offeredDiscount;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	@Override
	public String toString() {
		return "FilterFeildsDto [orderId=" + orderId + ", couponId=" + couponId + ", productId=" + productId
				+ ", couponNo=" + couponNo + ", discount=" + discount + ", type=" + type + ", validity=" + validity
				+ ", productName=" + productName + ", brand=" + brand + ", mrp=" + mrp + ", discountValue="
				+ discountValue + ", offeredDiscount=" + offeredDiscount + ", createdAt=" + createdAt + "]";
	}
	
}
