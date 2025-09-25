package com.pracking.parking.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateParkingLotRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String city;
    @NotBlank
    private String area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
