/*
package com.franchiseworld.jalad.controller;



import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.BusinessCourier;
import com.franchiseworld.jalad.model.Orders;
import com.franchiseworld.jalad.model.PersonalCourier;
import com.franchiseworld.jalad.model.Status;
import com.franchiseworld.jalad.modeldto.OrderDto;
import com.franchiseworld.jalad.service.OrderService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;  // Use OrderService interface

    @PreAuthorize("hasRole('PERSONAL_USER')")
    @PostMapping("/personal")
    public ResponseEntity<Orders> createPersonalCourier(@RequestBody PersonalCourier personalCourier) {
        Orders savedOrder = orderService.savePersonalCourier(personalCourier);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('BUSINESS_USER')")
    @PostMapping("/business")
    public ResponseEntity<Orders> createBusinessCourier(@RequestBody BusinessCourier businessCourier) {
        Orders savedOrder = orderService.saveBusinessCourier(businessCourier);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    //findAllPersonsalOrders By Contact Number
    @PreAuthorize("hasRole('PERSONAL_USER')")
    @GetMapping("/personal/findcontact")
    public Page<Object[]> getOrdersByContactNo(@RequestParam("contactNo") String contactNo,
                                               @RequestParam(value= "page",defaultValue = "0")int page,
                                               @RequestParam(value = "size",defaultValue = "10")int size)
    {
        Pageable pageable= PageRequest.of(page,size);
        return orderService.findOrdersByContactNo(contactNo,pageable);
    }

    //FindAllBusinessOrders Only Business Orders
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/AllbusinessOrders")
    public List<Object[]> getAllBusinessOrders() {
        return orderService.findAllBusinessOrders();
    }

    //FindAllBusinessOrdersUserID Only Business Orders
    @PreAuthorize("hasRole('BUSINESS_USER')")
    @GetMapping("/findBusinessOrdersByUserId/{userId}")
    public ResponseEntity<Page<Object[]>> findBusinessOrdersByUserId(@PathVariable Long userId,
                                                                     @RequestParam(value = "page",defaultValue = "0")int page,
                                                                     @RequestParam(value ="size",defaultValue = "10")int size)
    {
        Pageable pageable=PageRequest.of(page,size);
        Page<Object[]> businessOrders=orderService.findBusinessOrdersByUserId(userId,pageable);
        return ResponseEntity.ok(businessOrders);

    }

    // findbyuserbyUserId  (pagination)
    */
/*@GetMapping("/getOrdersByUserId/{userId}")
    public ResponseEntity<ApiResponse> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }*//*

//new change 18
    @PreAuthorize("hasRole('PERSONAL_USER'),('BUSINESS_USER')")
    @GetMapping("/getOrdersByUserId/{userId}")
    public ResponseEntity<ApiResponse> getOrdersByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return orderService.getOrdersByUserId(userId, pageable);
    }

    //business Count of StatusOrders(Status:- pending, deliverdy etc)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/countBusinessOrdersByStatusAndUserId/{userId}")
    public ResponseEntity<Map<Status, Long>> countBusinessOrdersByStatusAndUserId(@PathVariable Long userId) {
        Map<Status, Long> orderCounts = orderService.countBusinessOrdersByStatusAndUserId(userId);
        return ResponseEntity.ok(orderCounts);
    }

    //GetOrdersDates
    @PreAuthorize("hasRole('PERSONAL_USER'),('BUSINESS_USER')")
    @GetMapping("/getOrderDates/{orderId}")
    public ResponseEntity<Map<String, LocalDate>> getOrderDates(@PathVariable Long orderId) {
        Map<String, LocalDate> orderDates = orderService.getOrderDates(orderId);
        return ResponseEntity.ok(orderDates);
    }


    // for getting sumary of all orders in admin module
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/countOrdersByStatus")
    public ResponseEntity<Map<Status, Long>> countOrdersByStatus() {
        Map<Status, Long> orderCounts = orderService.countOrdersByStatus();
        return ResponseEntity.ok(orderCounts);
    }


    // update status, zone id,zone detail
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/statusDetail/{orderId}")
    public ResponseEntity<Orders> updateOrderStatusAndZone(
            @PathVariable Long orderId,
            @RequestParam String status,
            @RequestParam Long zoneId) {

        Orders updatedOrder = orderService.updateOrderStatusAndZone(orderId, status, zoneId);
        return ResponseEntity.ok(updatedOrder);
    }

    // update order details admin
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("updateOrder/{id}")
    public ResponseEntity<ApiResponse> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderUpdateDTO)
    {
        return orderService.updateOrder(id,orderUpdateDTO);
    }

    // delete order admin
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("deleteOrder/{id}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long id){
        return  orderService.deleteOrder(id);
    }

    // get all orders admin
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("getAllOrders")
    public ResponseEntity<ApiResponse> getAllOrders(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size){
        return orderService.getAllOrders(page,size);

    }

}*/
