package com.franchiseworld.jalad.serviceimplementation;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.Zone;
import com.franchiseworld.jalad.repo.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.franchiseworld.jalad.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private ZoneRepository zoneRepository;
    @Override
    public ResponseEntity<ApiResponse> findZoneByCityAndState(String city, String state) {
        try {
            Zone existingZone = zoneRepository.findZoneByCityAndState(city,state);
            if (existingZone==null){
                return ResponseEntity.ok()
                        .body(new ApiResponse(existingZone, true, 200, "Zone Found"));

            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, 404, "Zone not found with " + city +"and "+state));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, 500, "internal server error"));

        }
    }
}
