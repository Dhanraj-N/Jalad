package com.franchiseworld.jalad.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Zone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long zoneId;

	private String name;
	private String city;
	private String state;
	private String password;
	private Long zoneCode;
	

	@OneToMany(mappedBy = "zone", cascade = CascadeType.ALL)
    private Set<Orders> orders; // Reference to the Orders entity

}
