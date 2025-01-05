package com.franchiseworld.jalad.controller;

import com.franchiseworld.jalad.model.Status;
import com.franchiseworld.jalad.model.Zone;
import com.franchiseworld.jalad.modeldto.OrderDto;
import com.franchiseworld.jalad.modeldto.RateCalculator;
import com.franchiseworld.jalad.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.Admin;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
  @Autowired
    AdminService adminService;
  @Autowired
   private OrderService orderService;
    @Autowired
    private ZoneService zoneService;

    @Autowired
    private QueryService queryService;

    @Autowired
    private CalculatePriceService calculatePriceService;


    // Get summary of all orders by status
    @GetMapping("/orders/status/count")
    public ResponseEntity<Map<Status, Long>> countOrdersByStatus() {
        Map<Status, Long> orderCounts = orderService.countOrdersByStatus();
        System.out.println("print");
        return ResponseEntity.ok(orderCounts);
    }

    // get all orders admin
    @GetMapping("/orders")
    public ResponseEntity<ApiResponse> getAllOrders(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size){
        return orderService.getAllOrders(page,size);

    }

    // Calculate courier price
    @PostMapping("/orders/calculate-price")
    public double calculateCourierPrice(@RequestBody RateCalculator rateCalculator) {
        return calculatePriceService.calculatePrice(rateCalculator);
    }

  // Get all personal orders
  @GetMapping("/AllPersonalOrders")
    public List<Object[]> getAllPersonalOrders() {
        return orderService.findAllPersonalOrders();
    }

    // Get all business orders
    @GetMapping("/business/orders")
    public List<Object[]> getAllBusinessOrders() {
        return orderService.findAllBusinessOrders();
    }
    // Get all queries
    @GetMapping("/queries")
    public ResponseEntity<ApiResponse> getAllQueries() {
        return queryService.getAllQueries();
    }

    // Update query status
    @PutMapping("/queries/{id}/status")
    public ResponseEntity<ApiResponse> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return queryService.updateStatus(id, status);
    }

    // Find zone by city and state
    @GetMapping("/zones/search")
    public ResponseEntity<ApiResponse> findZoneByCityAndState(@RequestParam String city, @RequestParam String state) {
        return adminService.findZoneByCityAndState(city, state);
    }

    // Update order details (admin)
    @PatchMapping("/orders/{id}")
    public ResponseEntity<ApiResponse> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderUpdateDTO)
    {
        return orderService.updateOrder(id,orderUpdateDTO);
    }

    // Delete order (admin)
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long id){
        return  orderService.deleteOrder(id);
    }


    // Create zone
    @PostMapping("/zones")
    public ResponseEntity<Zone> createZone(@Valid @RequestBody Zone zone) {
        Zone createdZone = zoneService.createZone(zone);
        return ResponseEntity.ok(createdZone);
    }

}
