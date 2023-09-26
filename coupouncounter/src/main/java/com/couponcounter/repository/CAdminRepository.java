package com.couponcounter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.couponcounter.entity.Admin;

@Repository
public interface CAdminRepository extends JpaRepository<Admin, Integer> {

}
