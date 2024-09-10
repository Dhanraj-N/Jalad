package com.franchiseworld.jalad.model;

import java.time.LocalDate;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("Business")
public class BusinessCourier extends Orders {

    private String packageDetails;
    private String packageCover;
    private String packageSize;
    private LocalDate pickupDate;
}
