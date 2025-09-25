package com.pracking.parking.dto;

import com.pracking.parking.entity.SlotSize;
import jakarta.validation.constraints.NotNull;

public class VehicleArrivalRequest {
    @NotNull
    private SlotSize vehicleSize;

    public SlotSize getVehicleSize() {
        return vehicleSize;
    }

    public void setVehicleSize(SlotSize vehicleSize) {
        this.vehicleSize = vehicleSize;
    }
}
