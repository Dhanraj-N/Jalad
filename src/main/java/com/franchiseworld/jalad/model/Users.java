package com.franchiseworld.jalad.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
   /* @Column(unique = true, nullable = false)
    @NotBlank(message = "Email is required")
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email should be a valid gmail address")*/
   @NotBlank(message = "Email can't be blank")
   @Email(regexp = "[a-z0-9._$]+@[a-z]+\\.[a-z]{2,3}",message = "Enter proper Email")
   @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    private String password;
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Contact number is required")
    @Size(min = 10, max = 10, message = "Contact number must be exactly 10 digits")
    @Pattern(regexp = "^[0-9]*$", message = "Contact number must contain only digits")
    private String contactNo;
    
//    @JsonManagedReference
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Orders> orders;

//    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    

}
