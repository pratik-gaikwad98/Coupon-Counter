package com.couponcounter.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class RequestCouponDto {
	@JsonAlias({ "coupon_id" })
	@JsonInclude(Include.NON_NULL)
	private int id;

	@JsonAlias({ "coupon_no" })
	private String couponNo;

	@JsonAlias({ "discount" })
	private String discount;

	@JsonAlias({ "type" })
	private String type;

	@JsonAlias({ "validity" })
	private String validity;

	public RequestCouponDto() {

	}

	public RequestCouponDto(String CouponNo, String discount, String type, String validity) {
		this.couponNo = CouponNo;
		this.discount = discount;
		this.type = type;
		this.validity = validity;
	}

	

	public RequestCouponDto(int id, String couponNo, String discount, String type, String validity) {
		this.id = id;
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

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	@Override
	public String toString() {
		return "CouponDto [id=" + id + ", CouponNo=" + couponNo + ", discount=" + discount + ", type=" + type
				+ ", validity=" + validity + "]";
	}

}
