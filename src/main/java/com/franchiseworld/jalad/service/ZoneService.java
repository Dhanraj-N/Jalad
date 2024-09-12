package com.franchiseworld.jalad.service;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.Orders;
import com.franchiseworld.jalad.model.Zone;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

public interface ZoneService {
    Zone createZone(Zone zone);

    Optional<Zone> login(Long zoneCode, String password);


    /*ResponseEntity<ApiResponse> updateZone(Long zoneId, Zone zoneDetail);*/

    public ResponseEntity <List<Orders>> getAllTodayOrder(Long zoneId);

    List<Orders> getAllOrderByZoneId(Long zoneId);

    // Method to get all orders by zone name
    List<Orders> getAllOrderByZoneName(String zoneName);

    // Method to get a Zone by zone ID
    Zone getZoneById(Long zoneId);



    boolean changePassword(String name, String oldPassword, String newPassword,String reEnterNewPassword);

}
