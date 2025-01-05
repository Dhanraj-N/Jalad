package com.franchiseworld.jalad.modeldto;

import com.franchiseworld.jalad.model.Users;

import lombok.Data;

@Data
public class UsersDto {

	private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String contactNo;
    private String address;
	private String country;
	private String state;
	private String city;
	private String pincode;
	
	private String companyName;
	private double companyShippingVolume;
	
	
	private UsersDto convertToDTO(Users user) {
		UsersDto dto = new UsersDto();
	    dto.setId(user.getId());
	    dto.setFirstName(user.getFirstName());
	    dto.setLastName(user.getLastName());
	    dto.setEmail(user.getEmail());
	    dto.setContactNo(user.getContactNo());
	    // Set orders if needed
	    return dto;
	}
}
