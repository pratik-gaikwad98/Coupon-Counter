package com.couponcounter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.couponcounter.entity.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer>{
	public Coupon findByCouponNo(String coupounNo);

	@Query(value = "SELECT * FROM coupon where validity > now() and coupon_valid = 'true' ORDER BY RANDOM() LIMIT 1;", nativeQuery = true)
	public Coupon getRandomCoupon();

	@Query(value = "SELECT * FROM coupon where validity > now() and coupon_valid = 'true' ORDER BY coupon_id;", nativeQuery = true)
	public List<Coupon> getAllCoupon();
}
