
package com.franchiseworld.jalad.serviceimplementation;



import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.franchiseworld.jalad.model.*;
import com.franchiseworld.jalad.repo.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.repo.OrderRepository;
import com.franchiseworld.jalad.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ZoneRepository zoneRepository;

    @Override
    public Orders savePersonalCourier(PersonalCourier personalCourier) {
        // Business logic for saving a PersonalCourier order
        personalCourier.setStatus(Status.DATA_RECEIVED);
        return orderRepository.save(personalCourier);
    }

    @Override
    public Orders saveBusinessCourier(BusinessCourier businessCourier) {
        // Business logic for saving a BusinessCourier order
        businessCourier.setStatus(Status.DATA_RECEIVED);
        return orderRepository.save(businessCourier);
    }

    //FindByContactNO All Courier.
//    @Override
//    public List<PersonalCourierDTO> findByContactNo(Long contactNo) {
//        return orderRepository.findByContactNo(contactNo);
//    }

    @Override
    public List<Object[]> findOrdersByContactNo(Long contactNo) {
        return orderRepository.findByContactNo(contactNo);
    }

    //FindAllBusinessOrders Only Business Orders
    @Override
    public List<Object[]> findAllBusinessOrders() {
        return orderRepository.findAllBusinessOrders();
    }

    //FindAllBusinessOrdersByUserID Only Business Orders

    @Override
    public List<Object[]> findBusinessOrdersByUserId(Long userId) {
        return orderRepository.findBusinessOrdersByUserId(userId);
    }

    //Count of StatusOrders(Status:- pending, deliverdy etc)
    @Override
    public Map<Status, Long> countBusinessOrdersByStatusAndUserId(Long userId) {
        List<Object[]> results = orderRepository.countBusinessOrdersByStatusAndUserId(userId);
        Map<Status, Long> statusCountMap = new HashMap<>();

        for (Object[] result : results) {
            Status status = (Status) result[1]; // Cast to Status enum
            Long count = (Long) result[0]; // Cast to Long
            statusCountMap.put(status, count);
        }

        return statusCountMap;
    }

    //GetOrdersDates
    @Override
    public Map<String, LocalDate> getOrderDates(Long orderId) {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        Map<String, LocalDate> datesMap = new HashMap<>();

        if (order.getDataReceivedDate() != null) {
            datesMap.put("DATA_RECEIVED", order.getDataReceivedDate());
        }
        if (order.getPickupDoneDate() != null) {
            datesMap.put("PICKUP_DONE", order.getPickupDoneDate());
        }
        if (order.getInTransitDate() != null) {
            datesMap.put("INTRANSIT", order.getInTransitDate());
        }
        if (order.getReachedDestinationDate() != null) {
            datesMap.put("REACHED_DESTINATION", order.getReachedDestinationDate());
        }
        if (order.getOutForDeliveryDate() != null) {
            datesMap.put("OUT_OF_DELIVERY", order.getOutForDeliveryDate());
        }
        if (order.getDeliveredDate() != null) {
            datesMap.put("DELIVERED", order.getDeliveredDate());
        }

        return datesMap;
    }


    //getOrdersByuserid
    @Override
    public ResponseEntity<ApiResponse> getOrdersByUserId(Long id) {
        List<Orders> orders = orderRepository.findByUserId(id);
        if (!orders.isEmpty()) {
            return ResponseEntity.ok()
                    .body(new ApiResponse(orders, true, 200, "Courier found successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, 404, "Courier not found for id " + id));
        }
    }


    //////////////////////////update status, zone id,zone detail
    @Override
    public Orders updateOrderStatusAndZone(Long orderId, String status, Long zoneId) {
        Optional<Orders> existingOrderOpt = orderRepository.findById(orderId);
        if (existingOrderOpt.isPresent()) {
            Orders existingOrder = existingOrderOpt.get();

            // Update the status
            existingOrder.setStatus(Status.valueOf(status.toUpperCase()));

            // Update the zone if the zoneId is different
            if (!existingOrder.getZone().getZoneId().equals(zoneId)) {
                // Fetch the new zone
                Optional<Zone> newZoneOpt = zoneRepository.findById(zoneId);
                if (newZoneOpt.isPresent()) {
                    Zone newZone = newZoneOpt.get();
                    existingOrder.setZone(newZone);
                } else {
                    throw new RuntimeException("Zone not found");
                }
            }

            return orderRepository.save(existingOrder);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getByStatusCount() {
        return null;
    }

    // admin summery
    @Override
    public Map<Status, Long> countOrdersByStatus() {
        List<Object[]> results = orderRepository.countOrdersByStatus();
        Map<Status, Long> statusCountMap = new HashMap<>();

        for (Object[] result : results) {
            Status status = (Status) result[1]; // Cast to Status enum
            Long count = (Long) result[0]; // Cast to Long
            statusCountMap.put(status, count);
        }

        return statusCountMap;
    }

}




