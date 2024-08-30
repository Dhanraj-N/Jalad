package com.franchiseworld.jalad.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PersonalUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long personalUserId;

	private String firstName;
	private String lastName;
	private String emailId;
	private String address;
	private String country;
	private String state;
	private String city;
	private String pincode;
	private Long contactNo;
	private String password;

}
