package com.franchiseworld.jalad.controller;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.Orders;
import com.franchiseworld.jalad.model.Zone;
import com.franchiseworld.jalad.service.ZoneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Zone> createZone(@Valid @RequestBody Zone zone) {
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
  // get all Todays Order
  @GetMapping("/{zoneId}/todayorder")
  public ResponseEntity<Page<Orders>> getAllTodayOrder(@PathVariable ("zoneId") Long zoneId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size)  //(@PathVariable Long zoneId)
  {
      Page<Orders> ordersPage=zoneService.getAllTodayOrder(zoneId,page,size);
      return ResponseEntity.ok(ordersPage);
      //return zoneService.getAllTodayOrder(zoneId);
  }

  // getAllOrderByZoneId
  // Get paginated orders by zone ID
  @GetMapping("/{zoneId}/orders")
  public ResponseEntity<Page<Orders>> getOrderByZoneId(
          @PathVariable Long zoneId,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size) {

      Pageable pageable = PageRequest.of(page, size);
      Page<Orders> ordersPage = zoneService.getOrderByZoneId(zoneId, pageable);
      return ResponseEntity.ok(ordersPage);
  }

    // Endpoint to get all orders by zone name
    @GetMapping("/{zoneName}/orderName")
    public ResponseEntity<Page<Orders>> getAllOrderByZoneName(@PathVariable String zoneName,
                                                              @RequestParam (defaultValue = "0") int page,
                                                              @RequestParam (defaultValue = "10") int size )
    {
        Pageable pageable= PageRequest.of(page,size);
        Page<Orders> orderPage =zoneService.getAllOrderByZoneName(zoneName,pageable);
        if(orderPage.hasContent())
        {
            return ResponseEntity.ok(orderPage);
        }
        else
        {
            return ResponseEntity.notFound().build();
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