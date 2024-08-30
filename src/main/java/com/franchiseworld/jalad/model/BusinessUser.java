package com.franchiseworld.jalad.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class BusinessUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long businessUserId;

	private String firstName;
	private String lastName;
	private String emailId;
	private String companyName;
	private String mobileNo;
	private String password;
	private double companyPackageVolume;
	private double companyShippingVolume;

}
