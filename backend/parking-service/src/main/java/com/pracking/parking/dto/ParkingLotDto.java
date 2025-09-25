package com.pracking.parking.dto;

import java.util.List;

public class ParkingLotDto {
    private Long id;
    private String name;
    private String city;
    private String area;
    private double occupancyRate;
    private boolean needAdditionalSlots;
    private List<ParkingSlotDto> slots;

    public ParkingLotDto(Long id, String name, String city, String area, double occupancyRate, boolean needAdditionalSlots, List<ParkingSlotDto> slots) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.area = area;
        this.occupancyRate = occupancyRate;
        this.needAdditionalSlots = needAdditionalSlots;
        this.slots = slots;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }

    public double getOccupancyRate() {
        return occupancyRate;
    }

    public boolean isNeedAdditionalSlots() {
        return needAdditionalSlots;
    }

    public List<ParkingSlotDto> getSlots() {
        return slots;
    }
}
