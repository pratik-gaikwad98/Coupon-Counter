package com.couponcounter.exceptions;

public class InvalidUserException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

	public InvalidUserException() {
		
	}
	
	public InvalidUserException(String msg) {
		super(msg);
	}

}
