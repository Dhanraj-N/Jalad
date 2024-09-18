package com.franchiseworld.jalad.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "order_type", discriminatorType = DiscriminatorType.STRING)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Column(name = "orderDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate orderDate;
    private String createdBy; // Name of the placer
    @Size(max = 255, message = "pickupAddress cannot be longer than 255 characters")
    private String pickupAddress;
    @Size(max = 255, message = "deliveryAddress cannot be longer than 255 characters")
    private String deliveryAddress;


    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    @JsonBackReference
    private Users users;

    @ManyToOne
  @JoinColumn(name = "zone_id", nullable = false)
  private Zone zone;
/*
   // order chnages in zone details

    public Orders(Long orderId, Status status) {
        this.orderId = orderId;
        this.status = status;
    }*/


    //Handle only zone manager
    @Enumerated(EnumType.STRING)
	private Status status = Status.DATA_RECEIVED;
    public Orders() {
        if (this.orderDate == null) {
            this.orderDate = LocalDate.now();
        }
    }

    //Status Change well be automatically data change Handle Zone manager
    // Date fields for each status
    private LocalDate dataReceivedDate;
    private LocalDate pickupDoneDate;
    private LocalDate inTransitDate;
    private LocalDate reachedDestinationDate;
    private LocalDate outForDeliveryDate;
    private LocalDate deliveredDate;

    public void setStatus(Status status) {
        this.status = status;


        // Update corresponding date fields based on the status
        LocalDate currentDate = LocalDate.now();
        switch (status) {
            case DATA_RECEIVED:
                if (this.dataReceivedDate == null) {
                    this.dataReceivedDate = currentDate;
                }
                break;
            case PICKUP_DONE:
                if (this.dataReceivedDate != null && this.pickupDoneDate == null) {
                    this.pickupDoneDate = currentDate;
                }
                break;
            case INTRANSIT:
                if (this.pickupDoneDate != null && this.inTransitDate == null) {
                    this.inTransitDate = currentDate;
                }
                break;
            case REACHED_DESTINATION:
                if (this.inTransitDate != null && this.reachedDestinationDate == null) {
                    this.reachedDestinationDate = currentDate;
                }
                break;
            case OUT_OF_DELIVERY:
                if (this.reachedDestinationDate != null && this.outForDeliveryDate == null) {
                    this.outForDeliveryDate = currentDate;
                }
                break;
            case DELIVERED:
                if (this.outForDeliveryDate != null && this.deliveredDate == null) {
                    this.deliveredDate = currentDate;
                }
                break;
        }

    }
}
