
package com.franchiseworld.jalad.serviceimplementation;



import java.time.LocalDate;
import java.util.*;

import com.franchiseworld.jalad.model.*;
import com.franchiseworld.jalad.modeldto.OrderDto;
import com.franchiseworld.jalad.repo.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Override
    public Page<Object[]> findOrdersByContactNo(String contactNo, Pageable pageable)
    {
        return orderRepository.findByContactNo(contactNo,pageable);
    }

    //FindAllBusinessOrders Only Business Orders
    @Override
    public List<Object[]> findAllBusinessOrders() {
        return orderRepository.findAllBusinessOrders();
    }

    //FindAllBusinessOrdersByUserID Only Business Orders

    @Override
    public Page<Object[]> findBusinessOrdersByUserId(Long userId,Pageable pageable)
    {
        return orderRepository.findBusinessOrdersByUserId(userId,pageable);

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


    //update status, zone id,zone detail
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

    // update order details crud admin
    @Override
    public ResponseEntity<ApiResponse> updateOrder(Long id, OrderDto orderUpdateDTO) {
        Optional<Orders> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isPresent()) {
            Orders order = optionalOrder.get();

            // Update only fields that are not null in the DTO

            if (orderUpdateDTO.getCreatedBy() != null) {
                order.setCreatedBy(orderUpdateDTO.getCreatedBy());
            }

            if (orderUpdateDTO.getPickupAddress() != null) {
                order.setPickupAddress(orderUpdateDTO.getPickupAddress());
            }

            if (orderUpdateDTO.getDeliveryAddress() != null) {
                order.setDeliveryAddress(orderUpdateDTO.getDeliveryAddress());
            }

            // Handle status update and date changes
            if (orderUpdateDTO.getStatus() != null) {
                Status newStatus = Status.valueOf(orderUpdateDTO.getStatus());
                order.setStatus(newStatus); // This will automatically update the dates
            }

            // Update other date fields
            if (orderUpdateDTO.getDataReceivedDate() != null) {
                order.setDataReceivedDate(orderUpdateDTO.getDataReceivedDate());
            }

            if (orderUpdateDTO.getPickupDoneDate() != null) {
                order.setPickupDoneDate(orderUpdateDTO.getPickupDoneDate());
            }

            if (orderUpdateDTO.getInTransitDate() != null) {
                order.setInTransitDate(orderUpdateDTO.getInTransitDate());
            }

            if (orderUpdateDTO.getReachedDestinationDate() != null) {
                order.setReachedDestinationDate(orderUpdateDTO.getReachedDestinationDate());
            }

            if (orderUpdateDTO.getOutForDeliveryDate() != null) {
                order.setOutForDeliveryDate(orderUpdateDTO.getOutForDeliveryDate());
            }

            if (orderUpdateDTO.getDeliveredDate() != null) {
                order.setDeliveredDate(orderUpdateDTO.getDeliveredDate());
            }

            return ResponseEntity.ok().body(new ApiResponse(orderRepository.save(order), true, 200, "updated"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, 400, "order not found"));
        }
    }
    // delete order admin
    @Override
    public ResponseEntity<ApiResponse> deleteOrder(Long id) {
        Optional<Orders> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isPresent()) {
            orderRepository.deleteById(id);
            return ResponseEntity.ok()
                    .body(new ApiResponse(true, 200, "order deleted !"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, 400, "order not found"));
        }
    }

// get all order
    @Override
    public ResponseEntity<ApiResponse> getAllOrders( Integer page, Integer size) {

       /* Pageable pageable = PageRequest.of(page,size);
        Page<Object[]> ordersPage = orderRepository.findAllOrders(pageable);*/
        Pageable pageable = PageRequest.of(page,size);
        Page<Object[]>ordersPage = orderRepository.findAllOrders(pageable);

        // Prepare list of orders
        List<Map<String, Object>> ordersList = new ArrayList<>();
        for (Object[] order : ordersPage.getContent()) {
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("orderId", order[0]);

            orderMap.put("orderDate", order[2]);
            orderMap.put("createdBy", order[3]);
            orderMap.put("pickupAddress", order[4]);
            orderMap.put("deliveryAddress", order[5]);
            ordersList.add(orderMap);
        }

        // Prepare the response with pagination metadata
        Map<String, Object> pagination = new HashMap<>();
        pagination.put("content", ordersList);
        pagination.put("pageNumber", ordersPage.getNumber());
        pagination.put("pageSize", ordersPage.getSize());
        pagination.put("totalPages", ordersPage.getTotalPages());
        pagination.put("totalElements", ordersPage.getTotalElements());
        pagination.put("first", ordersPage.isFirst());
        pagination.put("last", ordersPage.isLast());
        return ResponseEntity.ok()
                .body(new ApiResponse(pagination,true,200,"Orders fetched"));
    }
}





