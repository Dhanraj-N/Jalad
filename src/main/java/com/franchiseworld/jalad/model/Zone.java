package com.franchiseworld.jalad.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private Status status;

}
