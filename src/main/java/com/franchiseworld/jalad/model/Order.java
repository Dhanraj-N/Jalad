package com.franchiseworld.jalad.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long shippingId;  // Auto-generated shippingId

    private LocalDate orderDate;      
    private String createdBy;  // Name of the placer
    private String pickupAddress;
    private String deliveryAddress;
    
    @Enumerated(EnumType.STRING)
    private Status status;  
    
}