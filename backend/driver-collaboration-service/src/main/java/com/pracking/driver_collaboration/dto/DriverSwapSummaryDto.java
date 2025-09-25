package com.pracking.driver_collaboration.dto;

public class DriverSwapSummaryDto {
    private long plannedTrips;
    private double totalSavedHours;

    public DriverSwapSummaryDto(long plannedTrips, double totalSavedHours) {
        this.plannedTrips = plannedTrips;
        this.totalSavedHours = totalSavedHours;
    }

    public long getPlannedTrips() {
        return plannedTrips;
    }

    public double getTotalSavedHours() {
        return totalSavedHours;
    }
}
