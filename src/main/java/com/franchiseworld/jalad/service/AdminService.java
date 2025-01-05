package com.franchiseworld.jalad.service;

import org.springframework.http.ResponseEntity;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.Admin;


public interface AdminService {
    ResponseEntity<ApiResponse> findZoneByCityAndState(String city, String state);
    ResponseEntity<ApiResponse> createAdmin(Admin admin);


 /*  ResponseEntity<ApiResponse> updateAdmin(Admin admin);

    ResponseEntity<ApiResponse> resetPassword(String emailId, String oldPassword, String newPassword);*/
}

