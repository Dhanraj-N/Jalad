package com.franchiseworld.jalad.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class PersonalCourier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personalCourierId;

    private Long contactNo;
    private String pickupAddress;
    private String deliveryAddress;
    private String packageDetails;
    private String packageCover;
    private String packageSize;
    private LocalDate pickupDate;


 // Many-to-many relationship with PersonalUser
    @ManyToMany(mappedBy = "couriers", cascade = CascadeType.ALL)
    private Set<PersonalUser> users;
}
