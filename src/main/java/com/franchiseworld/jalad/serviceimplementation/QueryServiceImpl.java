package com.franchiseworld.jalad.serviceimplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.QueryStatus;
import com.franchiseworld.jalad.repo.QueryRepository;
import com.franchiseworld.jalad.service.QueryService;
@Service
public class QueryServiceImpl implements QueryService {

	@Autowired
	QueryRepository queryRepository;

	@Override
	public ResponseEntity<ApiResponse> createQuery(Query query) {
		try {
			// Set the default status to NOT_SOLVED before saving
			query.setStatus(QueryStatus.NOT_RESOLVED);

			Query save = queryRepository.save(query);

			return ResponseEntity.ok()
					.body(new ApiResponse(save, true, 200, "Query Saved Successfully"));
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(false, 500, "Internal Server Error"));
		}
	}


//	@Override
//	public ResponseEntity<ApiResponse> getQueryById(Long id) {
//		Optional<Query> existingQuery = queryRepository.findById(id);
//		if(existingQuery.isPresent()) {
//			try {
//				return ResponseEntity.ok().body(new ApiResponse(existingQuery, true, 200, "Get Query By Id"));
//			} catch (Exception e) {
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, 400, "Bad Request"));
//			}
//		}else {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, 404, "Id Not Found"));
//		}
//	}

	@Override
	public ResponseEntity<ApiResponse> getAllQueries() {
		try {
			List<Query> list = queryRepository.findAll();
			return ResponseEntity.ok()
					.body(new ApiResponse(list, true, 200, "Get All Queries"));
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, 500, "Internal Server Error"));
		}
	}

	@Override
	public ResponseEntity<ApiResponse> updateStatus(Long id, String status) {
		return null;
	}

/*
	@Override
	public ResponseEntity<ApiResponse> updateStatus(Long id, String status) {
		Optional<Query> currentStatus = queryRepository.findById(id);
		if (currentStatus.isPresent()) {
			try {
				Query query = currentStatus.get();
				QueryStatus sts = QueryStatus.valueOf(status);
				query.setStatus(sts);
				queryRepository.save(query);
				return ResponseEntity.ok()
						.body(new ApiResponse(query, true, 200, "Status Update Successfully"));
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ApiResponse(false, 500, "Internal Server Error"));
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, 404, "Id Not Found"));
		}
	}*/
}