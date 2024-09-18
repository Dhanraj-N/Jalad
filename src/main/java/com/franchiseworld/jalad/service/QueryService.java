
package com.franchiseworld.jalad.service;

import org.springframework.http.ResponseEntity;
import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.Query;

public interface QueryService {

	ResponseEntity<ApiResponse> createQueryByUserId(Long userId, Query query);
	ResponseEntity<ApiResponse> getAllQueries();
	ResponseEntity<ApiResponse> updateStatus(Long id, String status);
}

