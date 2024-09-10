package com.franchiseworld.jalad.controller;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

}
