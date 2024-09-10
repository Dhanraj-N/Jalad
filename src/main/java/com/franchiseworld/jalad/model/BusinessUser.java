package com.franchiseworld.jalad.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("Business")
public class BusinessUser extends Users {
	
	private String companyName;	
	private double companyShippingVolume;

}
