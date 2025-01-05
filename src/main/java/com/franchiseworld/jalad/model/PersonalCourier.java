package com.franchiseworld.jalad.model;

import java.time.LocalDate;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
