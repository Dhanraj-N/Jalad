package com.franchiseworld.jalad.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.BusinessCourier;
import com.franchiseworld.jalad.model.Orders;
import com.franchiseworld.jalad.model.Status;
import com.franchiseworld.jalad.modeldto.OrderDto;

public interface OrderService {


    //save Courier
	OrderDto createPersonalCourier(OrderDto orderDto);
//    Orders saveBusinessCourier(BusinessCourier businessCourier);
	OrderDto createBusinessCourier(OrderDto orderDto);

    //findAll personal Orders By Contact Number
    Page<Object[]> findOrdersByContactNo(String contactNo, Pageable pageable);

  //FindAllBusinessOrders Only Business Orders
    List<Object[]> findAllBusinessOrders();

  //AllBusinessordersbyuserID
//  Page<Object[]> findBusinessOrdersByUserId(Long userId,Pageable pageable);
  ResponseEntity<Page<Object[]>> findBusinessOrdersForLoggedInUser();


    //Count of StatusOrders(Status:- pending, deliverdy etc)
//    Map<Status, Long> countBusinessOrdersByStatusAndUserId(Long userId);
  ResponseEntity<Map<Status, Long>> countBusinessOrdersByStatusForLoggedInUser();

    //GetOrdersDates web
    Map<String, LocalDate> getOrderDates(Long orderId);

   //getUsersById
/*
    ResponseEntity<ApiResponse> getOrdersByUserId(Long id);
*/
   ResponseEntity<ApiResponse> getOrdersByUserId(Long id, Pageable pageable);


    ///////////////update status, zone id,zone detail
    public Orders updateOrderStatusAndZone(Long orderId, String status, Long zoneId);
    // admin count
    ResponseEntity<ApiResponse> getByStatusCount();


// admin count summery
Map<Status, Long> countOrdersByStatus();

// update order details admin
ResponseEntity<ApiResponse> updateOrder(Long id, OrderDto orderUpdateDTO);

// delete order admin
ResponseEntity<ApiResponse> deleteOrder(Long id);


// get all order admin
    ResponseEntity<ApiResponse> getAllOrders( Integer page, Integer size);

    // Get all personal orders
    List<Object[]> findAllPersonalOrders();
    }