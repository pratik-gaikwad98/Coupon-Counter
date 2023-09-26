package com.couponcounter.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;


@Component
public class CouponDto {

	@JsonAlias({ "coupon_id" })
	@JsonProperty("coupon_id")
	@JsonInclude(Include.NON_NULL)
	private int id;

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

	public CouponDto() {
		super();
	}

	public CouponDto(Integer id, String couponNo, Integer discount, String type, LocalDate validity) {
		this.id = id;
		this.couponNo = couponNo;
		this.discount = discount;
		this.type = type;
		this.validity = validity;
	}

	public CouponDto(String couponNo, Integer discount, String type, LocalDate validity) {
		this.couponNo = couponNo;
		this.discount = discount;
		this.type = type;
		this.validity = validity;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
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

	@Override
	public String toString() {
		return "couponDto [id=" + id + ", couponNo=" + couponNo + ", discount=" + discount + ", type=" + type
				+ ", validity=" + validity + "]";
	}

}
