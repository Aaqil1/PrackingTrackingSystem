package com.pracking.parking.dto;

public class ParkingPredictionResponse {
    private Long parkingLotId;
    private double occupancyRate;
    private boolean needAdditionalSlots;

    public ParkingPredictionResponse(Long parkingLotId, double occupancyRate, boolean needAdditionalSlots) {
        this.parkingLotId = parkingLotId;
        this.occupancyRate = occupancyRate;
        this.needAdditionalSlots = needAdditionalSlots;
    }

    public Long getParkingLotId() {
        return parkingLotId;
    }

    public double getOccupancyRate() {
        return occupancyRate;
    }

    public boolean isNeedAdditionalSlots() {
        return needAdditionalSlots;
    }
}
