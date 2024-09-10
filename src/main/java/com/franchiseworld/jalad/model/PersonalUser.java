package com.franchiseworld.jalad.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("Personal")
public class PersonalUser extends Users {
	
	private String address;
	private String country;
	private String state;
	private String city;
	private String pincode;
}
