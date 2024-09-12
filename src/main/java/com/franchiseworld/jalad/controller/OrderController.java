package com.franchiseworld.jalad.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.BusinessCourier;
import com.franchiseworld.jalad.model.Orders;
import com.franchiseworld.jalad.model.PersonalCourier;
import com.franchiseworld.jalad.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;  // Use OrderService interface

    @PostMapping("/personal")
    public ResponseEntity<Orders> createPersonalCourier(@RequestBody PersonalCourier personalCourier) {
        Orders savedOrder = orderService.savePersonalCourier(personalCourier);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @PostMapping("/business")
    public ResponseEntity<Orders> createBusinessCourier(@RequestBody BusinessCourier businessCourier) {
        Orders savedOrder = orderService.saveBusinessCourier(businessCourier);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }
    
  //FindByContactNO All Courier.
//    @GetMapping("/personal/findcontact")
//    public ResponseEntity<List<PersonalCourierDTO>> findByContactNo(@RequestParam Long contactNo) {
//        List<PersonalCourierDTO> couriers = orderService.findByContactNo(contactNo);
//        return ResponseEntity.ok(couriers);
//    }
    //findAllPersonsalOrders By Contact Number
    @GetMapping("/personal/findcontact")
    public List<Object[]> getOrdersByContactNo(@RequestParam("contactNo") Long contactNo) {
        return orderService.findOrdersByContactNo(contactNo);
    }
    
  //FindAllBusinessOrders Only Business Orders
    @GetMapping("/AllbusinessOrders")
    public List<Object[]> getAllBusinessOrders() {
        return orderService.findAllBusinessOrders();
    }

    
    //FindAllBusinessOrdersUserID Only Business Orders

    @GetMapping("/findBusinessOrdersByUserId/{userId}")
    public ResponseEntity<List<Object[]>> findBusinessOrdersByUserId(@PathVariable Long userId) {
        List<Object[]> businessOrders = orderService.findBusinessOrdersByUserId(userId);
        return ResponseEntity.ok(businessOrders);
    }
    // findbyuserbyUserId
    @GetMapping("/getOrdersByUserId/{userId}")
    public ResponseEntity<ApiResponse> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    //business Count of StatusOrders(Status:- pending, deliverdy etc)
    @GetMapping("/countBusinessOrdersByStatusAndUserId/{userId}")
    public ResponseEntity<List<Object[]>> countBusinessOrdersByStatusAndUserId(@PathVariable Long userId) {
        List<Object[]> orderCounts = orderService.countBusinessOrdersByStatusAndUserId(userId);
        return ResponseEntity.ok(orderCounts);
    }

    // for getting sumary of all orders in admin module

    @GetMapping("/countOrdersByStatus")
    public ResponseEntity<List<Object[]>> countOrdersByStatus() {
        List<Object[]> orderCounts = orderService.countOrdersByStatus();
        return ResponseEntity.ok(orderCounts);
    }

    //zone
    //////// update status, zone id,zone detail
    @PutMapping("/statusDetail/{orderId}")
    public ResponseEntity<Orders> updateOrderStatusAndZone(
            @PathVariable Long orderId,
            @RequestParam String status,
            @RequestParam Long zoneId) {

        Orders updatedOrder = orderService.updateOrderStatusAndZone(orderId, status, zoneId);
        return ResponseEntity.ok(updatedOrder);
    }
}
