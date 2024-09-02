package com.franchiseworld.jalad.controller;

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
import com.franchiseworld.jalad.model.PersonalCourier;
import com.franchiseworld.jalad.model.PersonalUser;
import com.franchiseworld.jalad.service.PersonalUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class PersonalUserController {

    @Autowired
    private PersonalUserService personalUserService;


    //create PersonalUser
    @PostMapping("/saveuser")
    public ResponseEntity<ApiResponse> createPersonalUser(@Valid @RequestBody PersonalUser personalUser ) {
        return personalUserService.savePersonalUser(personalUser);
    }
    
    //login personalUser
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody PersonalUser loginRequest) {
        return personalUserService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
    }
    
    //updatePersonalUserByID
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePersonalUser(
            @PathVariable long id, @RequestBody PersonalUser personalUserDetails) {
        return personalUserService.updatePersonalUser(id, personalUserDetails);
    }
    
    //Personal Courier
    @PostMapping("/savecourier")
    public ResponseEntity<ApiResponse> createCourier(@RequestBody PersonalCourier personalCourier) {
        return personalUserService.savePersonalCourier(personalCourier);
    }

    //getPersonalCourier
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCourierById(@PathVariable Long id) {
        return personalUserService.getPersonalCourierById(id);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCourier(@PathVariable Long id, @RequestBody PersonalCourier personalCourierDetails) {
        return personalUserService.updatePersonalCourier(id, personalCourierDetails);
    }

     //get All couriers by contact number
    @GetMapping("/findByContactNo")
    public ResponseEntity<ApiResponse> getCouriersByContactNo(@RequestParam Long contactNo) {
        return personalUserService.getCouriersByContactNo(contactNo);
    }
}