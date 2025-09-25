package com.pracking.parking.entity;

import jakarta.persistence.*;

@Entity
public class ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;

    @Enumerated(EnumType.STRING)
    private SlotSize size;

    private boolean occupied;

    @ManyToOne(fetch = FetchType.LAZY)
    private ParkingLot parkingLot;

    public ParkingSlot() {
    }

    public ParkingSlot(String label, SlotSize size, boolean occupied) {
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

    public void setLabel(String label) {
        this.label = label;
    }

    public SlotSize getSize() {
        return size;
    }

    public void setSize(SlotSize size) {
        this.size = size;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
}
