package com.franchiseworld.jalad.controller;

import java.util.Optional;

import com.franchiseworld.jalad.model.Query;
import com.franchiseworld.jalad.service.OrderService;
import com.franchiseworld.jalad.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.Orders;
import com.franchiseworld.jalad.service.ZoneService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/zone")
public class ZoneController {
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private QueryService queryService;

    // Get all today's orders for a zone
    @GetMapping("/{zoneId}/orders/today")
  public ResponseEntity<ApiResponse> getAllTodayOrder(@PathVariable ("zoneId") Long zoneId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size)  //(@PathVariable Long zoneId)
  {
      return zoneService.getAllTodayOrder(zoneId,page,size);
      //return zoneService.getAllTodayOrder(zoneId);
  }

    // Update order status and assign zone
    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<Orders> updateOrderStatusAndZone(
            @PathVariable Long orderId,
            @RequestParam String status,
            @RequestParam Long zoneId) {

        Orders updatedOrder = orderService.updateOrderStatusAndZone(orderId, status, zoneId);
        return ResponseEntity.ok(updatedOrder);
    }

    //// change password id
/*@PutMapping("/{zoneId}/changePassword")
public ResponseEntity<String> changePassword(
        @PathVariable Long zoneId,
        @RequestParam String oldPassword,
        @RequestParam String newPassword)
{
    boolean isPasswordChanged = zoneService.changePassword(zoneId, oldPassword, newPassword);

    if (isPasswordChanged) {
        return ResponseEntity.ok("Password changed successfully");
    } else {
        return ResponseEntity.status(400).body("Old password is incorrect or zone not found");
    }
}*/
// Change password for a zone
 @PutMapping("/{name}/password")
    public ResponseEntity<String> changePassword(
            @PathVariable("name") String name,
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String reEnterNewPassword)
    {
        boolean isPasswordChanged = zoneService.changePassword(name, oldPassword, newPassword,reEnterNewPassword);

        if (isPasswordChanged) {
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.status(400).body("Old password is incorrect or zone not found");
        }
    }

    // create query
    @PostMapping("/query")
    public ResponseEntity<ApiResponse> createQueryByUserId(@RequestParam Long userId, @RequestBody Query query) {
        return queryService.createQueryByUserId(userId, query);
    }

}