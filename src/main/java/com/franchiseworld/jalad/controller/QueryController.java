package com.franchiseworld.jalad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.Query;
import com.franchiseworld.jalad.model.QueryStatus;
import com.franchiseworld.jalad.service.QueryService;
@RestController
@RequestMapping("/query")
public class QueryController {

	@Autowired
	QueryService queryService;

	@PostMapping("/createQuery")
	public ResponseEntity<ApiResponse> createQuery(@RequestBody Query query){
		return queryService.createQuery(query);
	}

	@GetMapping("/getAllQueries")
	public ResponseEntity<ApiResponse> getAllQueries(){
		return queryService.getAllQueries();
	}


	/*@PatchMapping("/updateStatus/{id}")
	public ResponseEntity<ApiResponse> updateStatus(@PathVariable Long id,@RequestBody String status){
		return queryService.updateStatus(id, status);
	}

	 */
	@PatchMapping("/updateStatus/{id}")
	public ResponseEntity<ApiResponse> updateStatus(@PathVariable Long id, @RequestBody String status) {
		return queryService.updateStatus(id, status);
	}


}
