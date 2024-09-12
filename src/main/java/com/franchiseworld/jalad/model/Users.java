package com.franchiseworld.jalad.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class Users {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = "First name is required")
    private String firstName;
    @Column(nullable = false)
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Column( unique = true, nullable = false)
    @NotBlank(message = "Email is required")
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email should be a valid gmail address")
    private String email;
    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    private String password;
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Contact number is required")
    @Size(min = 10, max = 10, message = "Contact number must be exactly 10 digits")
    @Pattern(regexp = "^[0-9]*$", message = "Contact number must contain only digits")
    private Long contactNo;

    @JsonManagedReference
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Orders> orders;
/*
    @OneToMany(mappedBy = "users") // To access queries for this user
    private Set<Query> queries;*/

    @JsonManagedReference
    @OneToMany(mappedBy = "users") // This will allow access to queries for this user
    private Set<Query> queries;

}


