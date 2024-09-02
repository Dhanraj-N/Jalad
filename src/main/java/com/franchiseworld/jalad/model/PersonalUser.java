package com.franchiseworld.jalad.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class PersonalUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String country;
	private String state;
	private String city;
	private String pincode;
	private long contactNo;
	private String password;
	
	
	// Many-to-many relationship with PersonalCourier
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "personal_user_courier", // Name of the join table
        joinColumns = @JoinColumn(name = "personal_user_id"), // Foreign key for PersonalUser
        inverseJoinColumns = @JoinColumn(name = "personal_courier_id") // Foreign key for PersonalCourier
    )
    private Set<PersonalCourier> couriers;

}
