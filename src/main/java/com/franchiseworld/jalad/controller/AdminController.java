package com.franchiseworld.jalad.controller;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.Admin;
import com.franchiseworld.jalad.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @GetMapping("/findZoneByCityAndState")
    public ResponseEntity<ApiResponse> findZoneByCityAndState(@RequestParam String city, @RequestParam String state){
        return  adminService.findZoneByCityAndState(city,state);
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<ApiResponse> createAdmin(@RequestBody Admin admin){
        return adminService.createAdmin(admin);
    }

    @PatchMapping("/updateAdmin")
    public ResponseEntity<ApiResponse> updateAdmin(@RequestBody Admin admin){
        return adminService.updateAdmin(admin);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam("emailId") String emailId,
                                                     @RequestParam("oldPassword") String oldPassword,
                                                     @RequestParam("newPassword") String newPassword){
        return adminService.resetPassword(emailId, oldPassword, newPassword);
    }

}
