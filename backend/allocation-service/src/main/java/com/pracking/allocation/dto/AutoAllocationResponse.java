package com.pracking.allocation.dto;

public class AutoAllocationResponse {
    private VehicleDto vehicle;
    private String allocationComment;

    public AutoAllocationResponse(VehicleDto vehicle, String allocationComment) {
        this.vehicle = vehicle;
        this.allocationComment = allocationComment;
    }

    public VehicleDto getVehicle() {
        return vehicle;
    }

    public String getAllocationComment() {
        return allocationComment;
    }
}
