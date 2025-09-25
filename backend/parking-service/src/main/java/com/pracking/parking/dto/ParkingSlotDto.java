package com.pracking.parking.dto;

import com.pracking.parking.entity.SlotSize;

public class ParkingSlotDto {
    private Long id;
    private String label;
    private SlotSize size;
    private boolean occupied;

    public ParkingSlotDto(Long id, String label, SlotSize size, boolean occupied) {
        this.id = id;
        this.label = label;
        this.size = size;
        this.occupied = occupied;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public SlotSize getSize() {
        return size;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
