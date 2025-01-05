package com.franchiseworld.jalad.serviceimplementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.Orders;
import com.franchiseworld.jalad.model.Zone;
import com.franchiseworld.jalad.repo.OrderRepository;
import com.franchiseworld.jalad.repo.ZoneRepository;
import com.franchiseworld.jalad.service.ZoneService;

@Service
public class ZoneServiceImpl implements ZoneService {
    @Autowired
    private ZoneRepository zoneRepository;
    @Autowired
    private OrderRepository orderRepository;  //new change

    @Override
    public Zone createZone(Zone zone) {
        return zoneRepository.save(zone);
    }

    @Override
    public Optional<Zone> login(Long zoneCode, String password) {
        return zoneRepository.findByZoneCodeAndPassword(zoneCode, password);
    }
    /*@Override
    public ResponseEntity<ApiResponse> updateZone(Long zoneId, Zone zoneDetail) {
        try
        {
            Optional<Zone> existingZoneDetail= zoneRepository.findById(zoneId);
            if(existingZoneDetail.isPresent())
            {
                Zone zone = existingZoneDetail.get();
                //zone.setZoneId(zoneDetail.getZoneId());  //new change
                zone.setName(zoneDetail.getName());
                zone.setCity(zoneDetail.getCity());
                zone.setState(zoneDetail.getState());
                zone.setPassword(zoneDetail.getPassword());

                // zone.setStatus(zoneDetail.getStatus());  //new comment

                return ResponseEntity.ok().body(new ApiResponse(zoneRepository.save(zone),true,200,"Zone Update"));
            }
            else
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,404,"Zone Not Found By Id " + zoneId));
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false,500,"Internal Server Error"));
        }

    }*/

    // todays orders
    /*@Override
    public Page<Orders> getAllTodayOrder(Long zoneId, int page, int size) {
        LocalDate today = LocalDate.now();  //new comment
        Pageable pageRequest = PageRequest.of(page, size);
        return orderRepository.findByZone_ZoneIdAndOrderDate(zoneId, today, pageRequest);

    }*/
    /////////////////new change
    @Override
    public ResponseEntity<ApiResponse> getAllTodayOrder(Long zoneId, int page, int size) {
        LocalDate today = LocalDate.now();  // Get today's date
        Pageable pageRequest = PageRequest.of(page, size);

        // Fetch the paginated data
        Page<Orders> ordersPage = orderRepository.findByZone_ZoneIdAndOrderDate(zoneId, today, pageRequest);

        // Prepare list of orders (without redundant pagination in content)
        List<Map<String, Object>> ordersList = new ArrayList<>();
        for (Orders order : ordersPage.getContent()) {
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("orderId", order.getOrderId());
            orderMap.put("orderDate", order.getOrderDate());
            orderMap.put("createdBy", order.getCreatedBy());
            orderMap.put("pickupAddress", order.getPickupAddress());
            orderMap.put("deliveryAddress", order.getDeliveryAddress());
            ordersList.add(orderMap);
        }

        // Prepare the final response map with pagination and orders content
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("content", ordersList);
        responseMap.put("pageNumber", ordersPage.getNumber());
        responseMap.put("pageSize", ordersPage.getSize());
        responseMap.put("totalPages", ordersPage.getTotalPages());
        responseMap.put("totalElements", ordersPage.getTotalElements());
        responseMap.put("first", ordersPage.isFirst());
        responseMap.put("last", ordersPage.isLast());

        return ResponseEntity.ok().body(new ApiResponse(responseMap,true,200,"Data fetched"));
    }
    // Method to get all orders by zone ID
    @Override
	public Page<Orders> getOrderByZoneId(Long zoneId, Pageable pageable) {
        return orderRepository.findByZone_ZoneId(zoneId, pageable);
    }


    // Method to get all orders by zone name
    @Override
	public Page<Orders> getAllOrderByZoneName(String zoneName, Pageable pageable)
    {
        return orderRepository.findByZone_Name(zoneName,pageable);
    }

    // Method to get Zone by zone ID
    @Override
	public Zone getZoneById(Long zoneId) {
        Optional<Zone> zone = zoneRepository.findById(zoneId);
        return zone.orElse(null); // Return the zone if found, otherwise return null
    }

    public ResponseEntity<ApiResponse> saveZone(Zone zone)
    {
        try
        {
            return ResponseEntity.ok()
                    .body(new ApiResponse(zoneRepository.save(zone),true,200,"Create Zone"));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false,500,"Internal Server Error"));
        }
    }

    ////change password
/*@Override
public boolean changePassword(Long zoneId, String oldPassword, String newPassword) {
    Optional<Zone> zoneOptional = zoneRepository.findById(zoneId);

    if (zoneOptional.isPresent()) {
        Zone zone = zoneOptional.get();

        // Check if the old password matches
        if (zone.getPassword().equals(oldPassword)) {
            // Update with the new password
            zone.setPassword(newPassword);
            zoneRepository.save(zone);
            return true; // Password changed successfully
        }
    }

    return false; // Password change failed
}*/
    @Override
    public boolean changePassword(String name, String oldPassword, String newPassword,String reEnterNewPassword) {
        //Optional<Zone> zoneOptional = zoneRepository.findById(name);
        Optional<Zone> zoneOptional = zoneRepository.findByName(name);
        if (zoneOptional.isPresent()) {
            Zone zone = zoneOptional.get();

            // Check if the old password matches
            if (zone.getPassword().equals(oldPassword)) {
                // Update with the new password
                zone.setPassword(newPassword);
                zoneRepository.save(zone);
                return true; // Password changed successfully
            }
        }

        return false; // Password change failed
    }

}