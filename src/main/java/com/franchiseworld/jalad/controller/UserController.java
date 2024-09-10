package com.franchiseworld.jalad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.BusinessUser;
import com.franchiseworld.jalad.model.PersonalUser;
import com.franchiseworld.jalad.model.Users;
import com.franchiseworld.jalad.service.UsersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
    private UsersService usersService;  // Use OrderService interface

    @PostMapping("/personal")
    public ResponseEntity<Users> createPersonalUser(@RequestBody PersonalUser personalUser ) {
        Users savedUser = usersService.savePersonalUsers(personalUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    

    @PostMapping("/business")
    public ResponseEntity<Users> createBusinessUser(@RequestBody BusinessUser businessUser ) {
        Users savedUser = usersService.saveBusinessUsers(businessUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    
    //login personalUser
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody Users loginRequest) {
        return usersService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
    }
    
    //update Users 
    @PutMapping("/personal/{id}")
    public ResponseEntity<PersonalUser> updatePersonalUser(
            @PathVariable Long id,
            @RequestBody PersonalUser personalUser) {
        // Update the PersonalUser details
        PersonalUser updatedUser = usersService.updatePersonalUser(
                id, 
                personalUser.getFirstName(), 
                personalUser.getLastName(), 
                personalUser.getEmail()
        );
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/business/{id}")
    public ResponseEntity<BusinessUser> updateBusinessUser(
            @PathVariable Long id,
            @RequestBody BusinessUser businessUser) {
        // Update the BusinessUser details
        BusinessUser updatedUser = usersService.updateBusinessUser(
                id,
                businessUser.getFirstName(),
                businessUser.getLastName(),
                businessUser.getEmail(),
                businessUser.getPassword(),
                businessUser.getCompanyName(),
                businessUser.getContactNo()
        );
        return ResponseEntity.ok(updatedUser);
    }
}
