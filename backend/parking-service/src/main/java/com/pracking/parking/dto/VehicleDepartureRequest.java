package com.pracking.parking.dto;

import jakarta.validation.constraints.NotNull;

public class VehicleDepartureRequest {
    @NotNull
    private Long slotId;

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }
}
