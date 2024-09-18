
package com.franchiseworld.jalad.controller;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.Query;
import com.franchiseworld.jalad.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/queries")
public class QueryController {

	@Autowired
	private QueryService queryService;

	@PostMapping("/create")
	public ResponseEntity<ApiResponse> createQueryByUserId(@RequestParam Long userId, @RequestBody Query query) {
		return queryService.createQueryByUserId(userId, query);
	}

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllQueries() {
		return queryService.getAllQueries();
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponse> updateStatus(@PathVariable Long id, @RequestParam String status) {
		return queryService.updateStatus(id, status);
	}
}

