package com.franchiseworld.jalad.service;

import org.springframework.http.ResponseEntity;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.BusinessUser;
import com.franchiseworld.jalad.model.PersonalUser;
import com.franchiseworld.jalad.model.Users;

public interface UsersService {

	Users savePersonalUsers(PersonalUser personalUser);
//	ResponseEntity<ApiResponse> savePersonalUser(PersonalUser personalUser);
	Users saveBusinessUsers(BusinessUser businessUser);
	
	ResponseEntity<ApiResponse> loginUser(String email, String password);
	

 // Method to update PersonalUser details
    PersonalUser updatePersonalUser(Long id, String firstName, String lastName, String email);
    
    // Method to update BusinessUser details
    BusinessUser updateBusinessUser(Long id, String firstName, String lastName, String email, String password, String companyName,String contactNo);
    
    
}

