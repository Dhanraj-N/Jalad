package com.franchiseworld.jalad.ResponseHandler;

import com.franchiseworld.jalad.modeldto.OrderDto;
import com.franchiseworld.jalad.modeldto.UsersDto;

public class ApiResponse {
    private boolean status;
    private String message;
    private int responseCode;
    private Object object;

    public ApiResponse() {
    }

    public ApiResponse(Object object, boolean status, int responseCode, String message) {
        this.object = object;
        this.status = status;
        this.message = message;
        this.responseCode = responseCode;
    }

    public ApiResponse(boolean status, int responseCode, String message) {
        this.status = status;
        this.message = message;
        this.responseCode = responseCode;
    }
    
 // New constructor for UsersDto
    public ApiResponse(boolean status, int responseCode, String message, UsersDto userDto) {
        this.status = status;
        this.message = message;
        this.responseCode = responseCode;
        this.object = userDto; // Set the UsersDto object here
    }


    public ApiResponse(boolean status, int responseCode, String message, OrderDto orderDto) {
    	this.status = status;
        this.message = message;
        this.responseCode = responseCode;
        this.object = orderDto;
	}

	public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
    
}
