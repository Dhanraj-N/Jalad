package com.franchiseworld.jalad.service;

import com.franchiseworld.jalad.modeldto.UsersDto;

public interface UsersService {

 // Method to update PersonalUser details
	 UsersDto updatePersonalUser(String currentUsername, UsersDto userDto);
//    PersonalUser updatePersonalUser(Long id, String firstName, String lastName, String email);

    // Method to update BusinessUser details
	 UsersDto updateBusinessUser(String currentUsername, UsersDto userDto);
//    BusinessUser updateBusinessUser(Long id, String firstName, String lastName, String email, String password, String companyName,String contactNo);


}

