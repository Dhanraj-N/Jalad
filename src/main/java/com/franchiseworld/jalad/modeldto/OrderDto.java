package com.franchiseworld.jalad.modeldto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OrderDto {
	
	private Long orderId;
	private String createdBy;
    private String pickupAddress;
    private String deliveryAddress;
    private Long contactNo;
    
    private String packageDetails;
    private String packageCover;
    private String packageSize;
    private LocalDate pickupDate;
    
    private LocalDate dataReceivedDate;
    private LocalDate pickupDoneDate;
    private LocalDate inTransitDate;
    private LocalDate reachedDestinationDate;
    private LocalDate outForDeliveryDate;
    private LocalDate deliveredDate;
    private String status;
    
    
}


