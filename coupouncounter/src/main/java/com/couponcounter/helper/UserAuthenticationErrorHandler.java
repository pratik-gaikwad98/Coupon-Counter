package com.couponcounter.helper;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.couponcounter.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserAuthenticationErrorHandler implements AuthenticationEntryPoint {

	
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) 
      throws IOException, ServletException {

    	ErrorResponse re = new ErrorResponse("error",HttpStatus.BAD_REQUEST,authException.getMessage(),""+UUID.randomUUID(),java.time.LocalDateTime.now());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        OutputStream responseStream = response.getOutputStream();
        mapper.registerModule(new JavaTimeModule());
        mapper.writeValue(responseStream, re);
        responseStream.flush();
    }
}