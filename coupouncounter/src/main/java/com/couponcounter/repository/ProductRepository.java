package com.couponcounter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.couponcounter.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	public List<Product> findByBrandIgnoreCase(String brand);
	
	public Product findByBrand(String brand);
	
	public Product findByProductName(String productName);

	public List<Product> findAllByOrderById();

}
