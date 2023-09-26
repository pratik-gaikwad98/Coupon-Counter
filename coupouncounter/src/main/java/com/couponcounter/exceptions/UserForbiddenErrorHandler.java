package com.couponcounter.exceptions;

public class UserForbiddenErrorHandler extends RuntimeException{

	private static final long serialVersionUID = -5821491268357170883L;

	public UserForbiddenErrorHandler() {
		
	}
	
	public UserForbiddenErrorHandler(String msg) {
		super(msg);
	}
}
