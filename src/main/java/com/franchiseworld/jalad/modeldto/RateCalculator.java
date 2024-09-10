package com.franchiseworld.jalad.modeldto;

public class RateCalculator {
	
	private double weight;
    private double distance;
	public RateCalculator(double weight, double distance) {
		this.weight = weight;
		this.distance = distance;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}

}
