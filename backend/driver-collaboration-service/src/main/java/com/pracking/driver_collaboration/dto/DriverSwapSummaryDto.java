package com.pracking.driver_collaboration.dto;

public class DriverSwapSummaryDto {
    private long plannedTrips;
    private double totalSavedHours;
    private double totalSavedCost;

    public DriverSwapSummaryDto(long plannedTrips, double totalSavedHours, double totalSavedCost) {
        this.plannedTrips = plannedTrips;
        this.totalSavedHours = totalSavedHours;
        this.totalSavedCost = totalSavedCost;
    }

    public long getPlannedTrips() {
        return plannedTrips;
    }

    public double getTotalSavedHours() {
        return totalSavedHours;
    }

    public double getTotalSavedCost() {
        return totalSavedCost;
    }
}
