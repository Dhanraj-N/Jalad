package com.franchiseworld.jalad.model;

import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Zone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long zoneId;
	@Column(nullable = false)
	@NotBlank(message = "name is required")
	private String name;
	@Size(max = 100, message = "City cannot be longer than 100 characters")
	private String city;
	@Size(max = 100, message = "State cannot be longer than 100 characters")
	private String state;
	@Column(nullable = false)
	@NotBlank(message = "Password is required")
	private String password;
	private Long zoneCode;
	

	@OneToMany(mappedBy = "zone", cascade = CascadeType.ALL)
    private Set<Orders> orders; // Reference to the Orders entity

}
