package com.franchiseworld.jalad.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adminId;
/*	@NotBlank(message = "Email can't be blank")
	@Email(regexp = "[a-z0-9._$]+@[a-z]+\\.[a-z]{2,3}",message = "Enter proper Email")
	@Column(unique = true,nullable = false)*/
	/*private String emailId;
	@Column(nullable = false)
	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	private String password;*/
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;



}
