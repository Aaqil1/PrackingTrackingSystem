package com.pracking.routing.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class RoutingRequest {
    @NotBlank
    private String currentCity;
    private String preferredArea;
    @NotBlank
    private String vehicleSize;
    @Min(0)
    private double maximumDistanceKm = 100;

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getPreferredArea() {
        return preferredArea;
    }

    public void setPreferredArea(String preferredArea) {
        this.preferredArea = preferredArea;
    }

    public String getVehicleSize() {
        return vehicleSize;
    }

    public void setVehicleSize(String vehicleSize) {
        this.vehicleSize = vehicleSize;
    }

    public double getMaximumDistanceKm() {
        return maximumDistanceKm;
    }

    public void setMaximumDistanceKm(double maximumDistanceKm) {
        this.maximumDistanceKm = maximumDistanceKm;
    }
}
