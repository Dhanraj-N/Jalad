package com.franchiseworld.jalad.modeldto;

import lombok.Data;

import java.time.LocalDate;

    @Data
    public class OrderDto {

        private String createdBy;
        private String pickupAddress;
        private String deliveryAddress;
        private LocalDate dataReceivedDate;
        private LocalDate pickupDoneDate;
        private LocalDate inTransitDate;
        private LocalDate reachedDestinationDate;
        private LocalDate outForDeliveryDate;
        private LocalDate deliveredDate;
        private String status;
    }


