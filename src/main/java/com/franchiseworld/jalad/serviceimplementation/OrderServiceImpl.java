/*
package com.franchiseworld.jalad.serviceimplementation;



import java.util.LinkedHashMap;
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
    public List<Object[]> countBusinessOrdersByStatusAndUserId(Long userId) {
        return orderRepository.countBusinessOrdersByStatusAndUserId(userId);
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
        try{
            List<Orders> orderList=orderRepository.findAll();
            Map<String,Integer> map = new LinkedHashMap<>();
            int doneCount=0;
            int cancelCount=0;
            int totalOrders=0;
            int inProcess=0;
            if (!orderList.isEmpty()){
                for (Orders order: orderList){
                    if( order.getStatus().isDone()){
                        doneCount++;
                    }
                    else if(order.getStatus().isCanceled()){
                        cancelCount++;
                    }
                    else{
                        inProcess++;
                    }
                    totalOrders++;
                }
                map.put("Total Order Done",doneCount);
                map.put("Total Orders Cancelled",cancelCount);
                map.put("In Delivery process",inProcess);
                map.put("Total Orders",totalOrders);
                return ResponseEntity.ok()
                        .body(new ApiResponse(map,true,200,"Orders Fetched Successfully"));

            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false,400,"Orders Not Found"));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false,500,"Internal server error"));

        }
    }
    //zonemanager
*/
/*    @Override
    public Orders updateOrderStatus(Long orderId, String status) {
        Optional<Orders> existingOrderOpt = orderRepository.findById(orderId);
        if (existingOrderOpt.isPresent()) {
            Orders existingOrder = existingOrderOpt.get();
            existingOrder.setStatus(Status.valueOf(status.toUpperCase()));
            return orderRepository.save(existingOrder);
        } else {
            throw new RuntimeException("Order not found");
        }*//*

    }

*/
