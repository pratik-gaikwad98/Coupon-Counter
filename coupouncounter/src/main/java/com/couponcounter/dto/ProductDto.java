package com.couponcounter.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class ProductDto {

	@JsonProperty("product_id")
	private int id;

	@JsonProperty("product_name")
	private String productName;

	@JsonProperty("brand")
	private String brand;

	@JsonProperty("mrp")
	private int mrp;

	@JsonProperty("created_at")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	@JsonInclude(Include. NON_NULL)
	private LocalDateTime createdAt;

	public ProductDto() {

	}

	public ProductDto(int id, String productName, String brand, int mrp, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.productName = productName;
		this.brand = brand;
		this.mrp = mrp;
		this.createdAt = createdAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "ProductDto [id=" + id + ", productName=" + productName + ", brand=" + brand + ", mrp=" + mrp
				+ ", created_at=" + createdAt + "]";
	}

}
