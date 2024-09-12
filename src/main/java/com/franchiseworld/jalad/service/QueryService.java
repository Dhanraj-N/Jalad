package com.franchiseworld.jalad.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.Query;
import com.franchiseworld.jalad.model.QueryStatus;

public interface QueryService {

	ResponseEntity<ApiResponse> createQuery(Query query);
	ResponseEntity<ApiResponse> getAllQueries();
	ResponseEntity<ApiResponse> updateStatus(Long id, String status);


}