package com.couponcounter.entity;

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
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
@Component
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "order_id")
	private int orderId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;

	@Column(name = "mrp")
	private int mrp;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "discount_value")
	private int discountValue;

	@Column(name = "offered_discount")
	private int offeredDiscount;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private Users user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "created_by_id")
	private Users createdBy;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "updated_by_id")
	private Users updatedBy;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public Orders() {

	}

	public Orders(Product product, Coupon coupon, int mrp, int discountValue,
			int offeredDiscount, Users users) {
		this.product = product;
		this.coupon = coupon;
		this.mrp = mrp;
		this.createdAt = java.time.LocalDateTime.now();
		this.discountValue = discountValue;
		this.offeredDiscount = offeredDiscount;
		this.createdBy = users;
		this.user = users;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
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

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
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

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Orders [orderId=" + orderId + ", product=" + product + ", coupon=" + coupon + ", mrp=" + mrp
				+ ", createdAt=" + createdAt + ", discountValue=" + discountValue + ", offeredDiscount="
				+ offeredDiscount + ", userId=" + user + "]";
	}

}
