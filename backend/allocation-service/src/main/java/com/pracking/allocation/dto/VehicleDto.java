package com.pracking.allocation.dto;

public class VehicleDto {
    private Long id;
    private String registrationNumber;
    private String model;
    private double maxVolume;
    private int maxQuantity;

    public VehicleDto(Long id, String registrationNumber, String model, double maxVolume, int maxQuantity) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.maxVolume = maxVolume;
        this.maxQuantity = maxQuantity;
    }

    public Long getId() {
        return id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public double getMaxVolume() {
        return maxVolume;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }
}
