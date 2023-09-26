package com.couponcounter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.couponcounter.exceptions.ExcelFileException;
import com.couponcounter.exceptions.InvalidCouponException;
import com.couponcounter.exceptions.InvalidFeildsException;
import com.couponcounter.exceptions.InvalidProductException;
import com.couponcounter.exceptions.InvalidUserException;
import com.couponcounter.exceptions.UserForbiddenErrorHandler;
import com.couponcounter.response.ErrorResponse;
import com.couponcounter.security.JwtRequestFilter;

@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler(value = InvalidCouponException.class)
	public ResponseEntity<?> invalidCoupon(InvalidCouponException ex, WebRequest webRequest) {
		ErrorResponse response = new ErrorResponse("error", HttpStatus.BAD_REQUEST, ex.getMessage(),
				JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = InvalidProductException.class)
	public ResponseEntity<?> InvalidProduct(InvalidProductException ex, WebRequest webRequest) {
		ErrorResponse response = new ErrorResponse("error", HttpStatus.BAD_REQUEST, ex.getMessage(),
				JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = ExcelFileException.class)
	public ResponseEntity<?> invalidExcelFile(ExcelFileException ex, WebRequest webRequest) {
		ErrorResponse response = new ErrorResponse("error", HttpStatus.BAD_REQUEST, ex.getMessage(),
				JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = InvalidFeildsException.class)
	public ResponseEntity<?> invalidFeildsFile(InvalidFeildsException ex, WebRequest webRequest) {
		ErrorResponse response = new ErrorResponse("error", HttpStatus.BAD_REQUEST, ex.getMessage(),
				JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public ResponseEntity<?> messageNotReadable(HttpMessageNotReadableException ex, WebRequest webRequest) {
		ErrorResponse response = new ErrorResponse("error", HttpStatus.BAD_REQUEST, ex.getMessage(),
				JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = InvalidUserException.class)
	public ResponseEntity<?> invalidUserDetailsException(InvalidUserException ex, WebRequest webRequest) {
		ErrorResponse response = new ErrorResponse("error", HttpStatus.BAD_REQUEST, ex.getMessage(),
				JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = UserForbiddenErrorHandler.class)
	public ResponseEntity<?> userForbiddenErrorHandler(UserForbiddenErrorHandler ex, WebRequest webRequest) {
		ErrorResponse response = new ErrorResponse("error", HttpStatus.BAD_REQUEST, ex.getMessage(),
				JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = org.springframework.security.access.AccessDeniedException.class)
	public ResponseEntity<?> accessDeniedException(org.springframework.security.access.AccessDeniedException ex, WebRequest webRequest) {
		ErrorResponse response = new ErrorResponse("error", HttpStatus.BAD_REQUEST, ex.getMessage(),
				JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = javax.crypto.BadPaddingException.class)
	public ResponseEntity<?> badPaddingException(javax.crypto.BadPaddingException ex, WebRequest webRequest) {
		ErrorResponse response = new ErrorResponse("error", HttpStatus.BAD_REQUEST, ex.getMessage(),
				JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	//org.springframework.web.bind.MethodArgumentNotValidException
	@ExceptionHandler(value = org.springframework.web.bind.MethodArgumentNotValidException.class)
	public ResponseEntity<?> invalidDateFormat(org.springframework.web.bind.MethodArgumentNotValidException ex, WebRequest webRequest) {
		ErrorResponse response = new ErrorResponse("error", HttpStatus.BAD_REQUEST, "invalid date, Date format should be YYYY-MM-DD",
				JwtRequestFilter.REQUEST_ID.get(), java.time.LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
