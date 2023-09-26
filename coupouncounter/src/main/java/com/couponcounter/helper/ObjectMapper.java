package com.couponcounter.helper;

import com.couponcounter.dto.CouponDto;
import com.couponcounter.dto.ProductDto;
import com.couponcounter.dto.UsersDto;
import com.couponcounter.entity.Coupon;
import com.couponcounter.entity.Product;
import com.couponcounter.entity.Users;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "default")
public interface ObjectMapper {
	
	public void updateCouponDtoToCoupon(CouponDto couponDto,@MappingTarget Coupon coupon);
	
	public void updateCouponToCouponDto(Coupon coupon,@MappingTarget CouponDto couponDto);
	
	public void updateProductToProductDto(Product product,@MappingTarget ProductDto productDto);

	public void updateProductDtoToProduct(ProductDto productDto,@MappingTarget Product product);

}

