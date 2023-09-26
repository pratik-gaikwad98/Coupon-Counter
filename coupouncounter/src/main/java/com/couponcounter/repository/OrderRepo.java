package com.couponcounter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.couponcounter.entity.Orders;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Integer> {

	public Orders findByProductId(int id);

	@Query(value = "SELECT * FROM orders o WHERE o.user_id = ?1", nativeQuery = true)
	public List<Orders> findOrderByUserId(int user);

}
