package com.franchiseworld.jalad.service;

import org.springframework.http.ResponseEntity;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.PersonalCourier;
import com.franchiseworld.jalad.model.PersonalUser;

public interface PersonalUserService {
	
	ResponseEntity<ApiResponse> savePersonalUser(PersonalUser personalUser);

//    ResponseEntity<ApiResponse> getPersonalUserById(int id);
	
	ResponseEntity<ApiResponse> loginUser(String email, String password);
	
    ResponseEntity<ApiResponse> updatePersonalUser(long id, PersonalUser personalUserDetails);
    
    //Courier
    ResponseEntity<ApiResponse> savePersonalCourier(PersonalCourier personalCourier);

    ResponseEntity<ApiResponse> getPersonalCourierById(Long id);
    
    ResponseEntity<ApiResponse> updatePersonalCourier(Long id, PersonalCourier personalCourierDetails);

    // get all couriers by contact number.
    ResponseEntity<ApiResponse> getCouriersByContactNo(Long contactNo);

}

