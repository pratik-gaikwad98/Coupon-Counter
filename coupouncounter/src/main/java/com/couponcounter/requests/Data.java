package com.couponcounter.requests;

import java.util.List;

import org.springframework.stereotype.Component;

import com.couponcounter.dto.ProductDto;
import com.couponcounter.dto.RequestCouponDto;
import com.fasterxml.jackson.annotation.JsonAlias;

@Component
public class Data {
	
	@JsonAlias("coupon")
	private RequestCouponDto coupon;
	
	@JsonAlias("product")
	private ProductDto product;
	
	@JsonAlias("file")
	private String file;
	
	@JsonAlias("param_data")
	private List<ParamData> paramData;

    public Data() {
		
	}

	public Data(RequestCouponDto coupon, ProductDto product, String file, List<ParamData> paramData) {
		super();
		this.coupon = coupon;
		this.product = product;
		this.file = file;
		this.paramData = paramData;
	}

	
	public RequestCouponDto getCoupon() {
		return coupon;
	}

	public void setCoupon(RequestCouponDto coupon) {
		this.coupon = coupon;
	}

	public ProductDto getProduct() {
		return product;
	}

	public void setProduct(ProductDto product) {
		this.product = product;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public List<ParamData> getParamData() {
		return paramData;
	}

	public void setParamData(List<ParamData> paramData) {
		this.paramData = paramData;
	}

	@Override
	public String toString() {
		return "Data [coupon=" + coupon + ", product=" + product + ", paramData=" + paramData + "]";
	}

}
