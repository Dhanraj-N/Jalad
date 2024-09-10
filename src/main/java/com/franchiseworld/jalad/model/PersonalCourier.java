package com.franchiseworld.jalad.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("Personal")
public class PersonalCourier extends Orders {

    private Long contactNo;
    private String packageDetails;
    private String packageCover;
    private String packageSize;
    private LocalDate pickupDate;


}
