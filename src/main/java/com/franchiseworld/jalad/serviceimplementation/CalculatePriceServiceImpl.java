package com.franchiseworld.jalad.serviceimplementation;

import org.springframework.stereotype.Service;

import com.franchiseworld.jalad.modeldto.RateCalculator;
import com.franchiseworld.jalad.service.CalculatePriceService;

@Service
public class CalculatePriceServiceImpl implements CalculatePriceService{

	@Override
	public double calculatePrice(RateCalculator rateCalculator) {
		double weight = rateCalculator.getWeight();
        double distance = rateCalculator.getDistance();

        if (weight <= 1) {
            if (distance <= 1) {
				return 35;
			} else if (distance <= 5) {
				return 45;
			} else if (distance <= 10) {
				return 55;
			} else if (distance <= 50) {
				return 75;
			} else if (distance <= 100) {
				return 95;
			} else {
				return 120;
			}
        } else if (weight <= 5) {
            if (distance <= 1) {
				return 70;
			} else if (distance <= 5) {
				return 90;
			} else if (distance <= 10) {
				return 110;
			} else if (distance <= 50) {
				return 150;
			} else if (distance <= 100) {
				return 190;
			} else {
				return 240;
			}
        } else if (weight <= 10) {
            if (distance <= 1) {
				return 110;
			} else if (distance <= 5) {
				return 140;
			} else if (distance <= 10) {
				return 170;
			} else if (distance <= 50) {
				return 230;
			} else if (distance <= 100) {
				return 290;
			} else {
				return 360;
			}
        } else if (weight <= 50) {
            if (distance <= 1) {
				return 210;
			} else if (distance <= 5) {
				return 250;
			} else if (distance <= 10) {
				return 290;
			} else if (distance <= 50) {
				return 370;
			} else if (distance <= 100) {
				return 450;
			} else {
				return 550;
			}
        } else if (weight <= 100) {
            if (distance <= 1) {
				return 420;
			} else if (distance <= 5) {
				return 480;
			} else if (distance <= 10) {
				return 540;
			} else if (distance <= 50) {
				return 660;
			} else if (distance <= 100) {
				return 780;
			} else {
				return 950;
			}
        } else if (weight <= 1000) {
            if (distance <= 1) {
				return 850;
			} else if (distance <= 5) {
				return 950;
			} else if (distance <= 10) {
				return 1050;
			} else if (distance <= 50) {
				return 1250;
			} else if (distance <= 100) {
				return 1450;
			} else {
				return 1750;
			}
        } else if (weight <= 10000) {
            if (distance <= 1) {
				return 4300;
			} else if (distance <= 5) {
				return 4750;
			} else if (distance <= 10) {
				return 5200;
			} else if (distance <= 50) {
				return 6200;
			} else if (distance <= 100) {
				return 7200;
			} else {
				return 8700;
			}
        } else if (weight <= 40000) {
            if (distance <= 1) {
				return 8600;
			} else if (distance <= 5) {
				return 9500;
			} else if (distance <= 10) {
				return 10400;
			} else if (distance <= 50) {
				return 12400;
			} else if (distance <= 100) {
				return 14400;
			} else {
				return 17400;
			}
        }
		return 0;
	}

}
