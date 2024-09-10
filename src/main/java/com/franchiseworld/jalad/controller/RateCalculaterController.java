package com.franchiseworld.jalad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchiseworld.jalad.modeldto.RateCalculator;
import com.franchiseworld.jalad.service.CalculatePriceService;

@RestController
@RequestMapping("/api/rate")
public class RateCalculaterController {
	
	@Autowired
	private CalculatePriceService calculatePriceService;

	@PostMapping("/calculate")
    public double calculateCourierPrice(@RequestBody RateCalculator rateCalculator) {
        return calculatePriceService.calculatePrice(rateCalculator);
    }
}
