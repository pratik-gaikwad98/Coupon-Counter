package com.couponcounter.exceptions;

public class InvalidCouponException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalidCouponException() {
		
	}
	
	public InvalidCouponException(String msg) {
		super(msg);
	}
}