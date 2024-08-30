package com.franchiseworld.jalad.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class BusinessCourier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long businessCourierId;

	private String pickupAddress;
	private String deliveryAddress;
	private String packageDetails;
	private String packageCover;
	private String packageSize;
	private LocalDate pickupDate;

}
