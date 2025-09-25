package com.pracking.routing.entity;

import jakarta.persistence.*;

@Entity
public class ParkingOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lotName;
    private String city;
    private String area;
    private double distanceKm;
    private int availableSlots;
    private String supportedSizes;

    public ParkingOption() {
    }

    public ParkingOption(String lotName, String city, String area, double distanceKm, int availableSlots, String supportedSizes) {
        this.lotName = lotName;
        this.city = city;
        this.area = area;
        this.distanceKm = distanceKm;
        this.availableSlots = availableSlots;
        this.supportedSizes = supportedSizes;
    }

    public Long getId() {
        return id;
    }

    public String getLotName() {
        return lotName;
    }

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public String getSupportedSizes() {
        return supportedSizes;
    }
}
