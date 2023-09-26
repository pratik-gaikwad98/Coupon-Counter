package com.couponcounter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.couponcounter.helper.ObjectMapperImpl;

@Configuration
public class MapperConfi {

	@Bean
	public ObjectMapperImpl EmployeesBean(){
	    return new ObjectMapperImpl();
	}
}
