package com.franchiseworld.jalad.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.franchiseworld.jalad.model.Status;
import org.springframework.http.ResponseEntity;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.BusinessCourier;
import com.franchiseworld.jalad.model.Orders;
import com.franchiseworld.jalad.model.PersonalCourier;

public interface OrderService {


    //save Courier
    Orders savePersonalCourier(PersonalCourier personalCourier);
    Orders saveBusinessCourier(BusinessCourier businessCourier);
    
    //FindByContactNO All Courier.
    List<Object[]> findOrdersByContactNo(Long contactNo);
    
  //FindAllBusinessOrders Only Business Orders
    List<Object[]> findAllBusinessOrders();
    
  //AllBusinessordersbyuserID
    List<Object[]> findBusinessOrdersByUserId(Long userId);

    //Count of StatusOrders(Status:- pending, deliverdy etc)
    Map<Status, Long> countBusinessOrdersByStatusAndUserId(Long userId);

    //GetOrdersDates web
    Map<String, LocalDate> getOrderDates(Long orderId);

   //getUsersById
    ResponseEntity<ApiResponse> getOrdersByUserId(Long id);


    ///////////////update status, zone id,zone detail
    public Orders updateOrderStatusAndZone(Long orderId, String status, Long zoneId);
    // admin count
    ResponseEntity<ApiResponse> getByStatusCount();


// admin count summery
Map<Status, Long> countOrdersByStatus();



    }