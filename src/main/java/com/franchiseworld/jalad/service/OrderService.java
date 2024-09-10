package com.franchiseworld.jalad.service;


import java.util.List;

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
    List<Object[]> countBusinessOrdersByStatusAndUserId(Long userId);

  //getUsersById
    ResponseEntity<ApiResponse> getOrdersByUserId(Long id);

   /* //update status in zonemanager
    Orders updateOrderStatus(Long orderId, String status);*/

    //update status, zone id,zone detail
    public Orders updateOrderStatusAndZone(Long orderId, String status, Long zoneId);
    ResponseEntity<ApiResponse> getByStatusCount();

}
