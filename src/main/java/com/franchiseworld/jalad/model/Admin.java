package com.franchiseworld.jalad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adminId;
	@Column( unique = true, nullable = false)
	@NotBlank(message = "Email is required")
	@Email(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email should be a valid gmail address")
	private String emailId;
	@Column(nullable = false)
	@NotBlank(message = "Password is required")
	private String password;
	public Long getAdminId() {

		return adminId;
	}
	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
	public String getEmailId() {

		return emailId;
	}
	public void setEmailId(String emailId) {

		this.emailId = emailId;
	}
	public String getPassword() {

		return password;
	}
	public void setPassword(String password) {

		this.password = password;
	}
	

}
