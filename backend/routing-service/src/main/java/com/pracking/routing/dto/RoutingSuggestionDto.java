package com.pracking.routing.dto;

public class RoutingSuggestionDto {
    private Long optionId;
    private String lotName;
    private String city;
    private String area;
    private double distanceKm;
    private int availableSlots;
    private String supportedSizes;

    public RoutingSuggestionDto(Long optionId, String lotName, String city, String area, double distanceKm, int availableSlots, String supportedSizes) {
        this.optionId = optionId;
        this.lotName = lotName;
        this.city = city;
        this.area = area;
        this.distanceKm = distanceKm;
        this.availableSlots = availableSlots;
        this.supportedSizes = supportedSizes;
    }

    public Long getOptionId() {
        return optionId;
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
