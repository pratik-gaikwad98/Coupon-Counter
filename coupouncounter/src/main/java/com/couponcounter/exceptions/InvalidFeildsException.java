package com.couponcounter.exceptions;

public class InvalidFeildsException extends Exception {

	private static final long serialVersionUID = 1L;
public InvalidFeildsException() {
		
	}
	
	public InvalidFeildsException(String msg) {
		super(msg);
	}
}
