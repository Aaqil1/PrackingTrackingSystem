package com.pracking.parking.entity;

public enum SlotSize {
    SMALL,
    MEDIUM,
    LARGE;

    public boolean canFit(SlotSize vehicleSize) {
        return this.ordinal() >= vehicleSize.ordinal();
    }
}
