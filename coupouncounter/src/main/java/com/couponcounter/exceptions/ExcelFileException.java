package com.couponcounter.exceptions;

public class ExcelFileException extends Exception {

	private static final long serialVersionUID = 1L;

	public ExcelFileException() {
		
	}
	
	public ExcelFileException(String msg) {
		super(msg);
	}
}
