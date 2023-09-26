package com.couponcounter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.couponcounter.entity.UserAuthenticationDetail;

@Repository
public interface UserAuthenticationRepository extends JpaRepository<UserAuthenticationDetail, String> {

}
