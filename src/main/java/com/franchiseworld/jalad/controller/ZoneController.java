package com.franchiseworld.jalad.controller;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.Orders;
import com.franchiseworld.jalad.model.Zone;
import com.franchiseworld.jalad.service.ZoneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/zone")
public class ZoneController {
    @Autowired
    private ZoneService zoneService;
    ///
    @PostMapping("/create")
    public ResponseEntity<Zone> createZone(@RequestBody Zone zone) {
        Zone createdZone = zoneService.createZone(zone);
        return ResponseEntity.ok(createdZone);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam Long zoneCode, @RequestParam String password) {
        Optional<Zone> zone = zoneService.login(zoneCode, password);
        if (zone.isPresent()) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

  /*  @PutMapping("updatezone/{zoneId}")
    public ResponseEntity<ApiResponse> updateZone(@PathVariable Long zoneId, @Valid @RequestBody Zone zoneDetail)
    {
        return zoneService.updateZone(zoneId,zoneDetail);
    }*/
    // Todays Order
    @GetMapping("/{zoneId}/todayorder")
    public ResponseEntity<List<Orders>> getAllTodayOrder(@PathVariable ("zoneId") Long zoneId)  //(@PathVariable Long zoneId)
    {
        return zoneService.getAllTodayOrder(zoneId);
    }
    @GetMapping("/{zoneId}/order")
    public List<Orders> getAllOrderByZoneId(@PathVariable Long zoneId) {
        return zoneService.getAllOrderByZoneId(zoneId);
    }

    // Endpoint to get all orders by zone name
    @GetMapping("/{zoneName}/orderName")
    public ResponseEntity<List<Orders>> getAllOrderByZoneName(@RequestParam String zoneName) {
        List<Orders> order = zoneService.getAllOrderByZoneName(zoneName);
        if (order != null && !order.isEmpty())
        {
            return ResponseEntity.ok(order); // Return 200 OK with the list of orders
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if no orders are found
        }
    }
//getinformationbyzone id
    @GetMapping("/{zoneId}")
    public ResponseEntity<Zone> getZoneById(@PathVariable Long zoneId) {
        Zone zone = zoneService.getZoneById(zoneId);
        if (zone != null) {
            return ResponseEntity.ok(zone); // Return 200 OK with the Zone data
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if the Zone is not found
        }
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
    //  changePassword  name

    @PutMapping("/{name}/changePassword")
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

}