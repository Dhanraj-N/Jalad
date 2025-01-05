package com.franchiseworld.jalad.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	@Size(min = 8, message = "Password must be at least 8 characters long")
	private String password;
	private Long zoneCode;

	// order chnages in zone details

	/*public Zone(Long zoneId, Status status) {
		this.name = name;
		this.status = status;
	}*/
	// zone detailas order fetch
	@OneToMany(mappedBy = "zone", cascade = CascadeType.ALL)
    private Set<Orders> orders; // Reference to the Orders entity


	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
	
	// This is the attribute you should check or add
    private boolean isDefault=true;

}
