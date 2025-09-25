package com.pracking.driver_collaboration.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class DriverSwapPlanDto {
    private Long id;
    private String originBranch;
    private String destinationBranch;
    private LocalDate departureDate;
    private String driversInvolved;
    private LocalTime shiftStartTime;
    private LocalTime shiftEndTime;
    private LocalTime returnTripStartTime;
    private double savedWaitTimeHours;
    private double savedWaitCost;
    private String notes;

    public DriverSwapPlanDto(Long id, String originBranch, String destinationBranch, LocalDate departureDate, String driversInvolved,
                             LocalTime shiftStartTime, LocalTime shiftEndTime, LocalTime returnTripStartTime,
                             double savedWaitTimeHours, double savedWaitCost, String notes) {
        this.id = id;
        this.originBranch = originBranch;
        this.destinationBranch = destinationBranch;
        this.departureDate = departureDate;
        this.driversInvolved = driversInvolved;
        this.shiftStartTime = shiftStartTime;
        this.shiftEndTime = shiftEndTime;
        this.returnTripStartTime = returnTripStartTime;
        this.savedWaitTimeHours = savedWaitTimeHours;
        this.savedWaitCost = savedWaitCost;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public String getOriginBranch() {
        return originBranch;
    }

    public String getDestinationBranch() {
        return destinationBranch;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public String getDriversInvolved() {
        return driversInvolved;
    }

    public LocalTime getShiftStartTime() {
        return shiftStartTime;
    }

    public LocalTime getShiftEndTime() {
        return shiftEndTime;
    }

    public LocalTime getReturnTripStartTime() {
        return returnTripStartTime;
    }

    public double getSavedWaitTimeHours() {
        return savedWaitTimeHours;
    }

    public double getSavedWaitCost() {
        return savedWaitCost;
    }

    public String getNotes() {
        return notes;
    }
}
