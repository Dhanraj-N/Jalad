package com.franchiseworld.jalad.service;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<ApiResponse> findZoneByCityAndState(String city, String state);
}
