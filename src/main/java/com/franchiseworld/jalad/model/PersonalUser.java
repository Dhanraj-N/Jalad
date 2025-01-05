package com.franchiseworld.jalad.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("Personal")
public class PersonalUser extends Users {
	@Size(max = 255, message = "Address cannot be longer than 255 characters")
	private String address;
	@Size(max = 100, message = "Country cannot be longer than 100 characters")
	private String country;
	@Size(max = 100, message = "State cannot be longer than 100 characters")
	private String state;
	@Size(max = 100, message = "City cannot be longer than 100 characters")
	private String city;
	@Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be exactly 6 digits")
	private String pincode;

}
