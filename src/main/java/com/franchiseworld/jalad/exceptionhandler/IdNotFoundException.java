package com.franchiseworld.jalad.exceptionhandler;

public class IdNotFoundException extends RuntimeException {

	private  String message;

	public IdNotFoundException(String message) {
		this.message = message;
	}

	public IdNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}


	

}
