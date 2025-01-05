
package com.franchiseworld.jalad.serviceimplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.Query;
import com.franchiseworld.jalad.model.QueryStatus;
import com.franchiseworld.jalad.model.Users;
import com.franchiseworld.jalad.repo.QueryRepository;
import com.franchiseworld.jalad.repo.UsersRepository;
import com.franchiseworld.jalad.service.QueryService;

@Service
public class QueryServiceImpl implements QueryService {

	@Autowired
	private QueryRepository queryRepository;

	@Autowired
	private UsersRepository userRepository;

	@Override
	public ResponseEntity<ApiResponse> createQueryByUserId(Long userId, Query query) {
		Users user = userRepository.findById(userId).orElse(null);

		if (user == null) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, 400, "User not found"));
		}

		query.setUser(user);
		query.setStatus(QueryStatus.NOT_RESOLVED);
		queryRepository.save(query);

		return ResponseEntity.ok(new ApiResponse(query, true, 200, "Query created successfully"));
	}

	@Override
	public ResponseEntity<ApiResponse> getAllQueries() {
		return ResponseEntity.ok(new ApiResponse(queryRepository.findAll(), true, 200, "Fetched all queries"));
	}

	@Override
	public ResponseEntity<ApiResponse> updateStatus(Long id, String status) {
		Query query = queryRepository.findById(id).orElse(null);

		if (query == null) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, 400, "Query not found"));
		}

		try {
			QueryStatus queryStatus = QueryStatus.valueOf(status);
			query.setStatus(queryStatus);
			queryRepository.save(query);
			return ResponseEntity.ok(new ApiResponse(query, true, 200, "Query status updated successfully"));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, 400, "Invalid status value"));
		}
	}
}

