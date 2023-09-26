package com.couponcounter.exceptions;

public class InvalidProductException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalidProductException() {
		
	}
	
	public InvalidProductException(String msg) {
		super(msg);
	}
}