package com.pracking.driver_collaboration.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class DriverSwapPlanRequest {
    @NotBlank
    private String originBranch;
    @NotBlank
    private String destinationBranch;
    @FutureOrPresent
    private LocalDate departureDate;
    @NotBlank
    private String driversInvolved;
    @NotNull
    private LocalTime shiftStartTime;
    @NotNull
    private LocalTime shiftEndTime;
    @NotNull
    private LocalTime returnTripStartTime;
    @DecimalMin(value = "0.0", inclusive = true)
    private double originalWaitTimeHours;
    @DecimalMin(value = "0.0", inclusive = true)
    private double hourlyRate;
    private String notes;

    public String getOriginBranch() {
        return originBranch;
    }

    public void setOriginBranch(String originBranch) {
        this.originBranch = originBranch;
    }

    public String getDestinationBranch() {
        return destinationBranch;
    }

    public void setDestinationBranch(String destinationBranch) {
        this.destinationBranch = destinationBranch;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public String getDriversInvolved() {
        return driversInvolved;
    }

    public void setDriversInvolved(String driversInvolved) {
        this.driversInvolved = driversInvolved;
    }

    public LocalTime getShiftStartTime() {
        return shiftStartTime;
    }

    public void setShiftStartTime(LocalTime shiftStartTime) {
        this.shiftStartTime = shiftStartTime;
    }

    public LocalTime getShiftEndTime() {
        return shiftEndTime;
    }

    public void setShiftEndTime(LocalTime shiftEndTime) {
        this.shiftEndTime = shiftEndTime;
    }

    public LocalTime getReturnTripStartTime() {
        return returnTripStartTime;
    }

    public void setReturnTripStartTime(LocalTime returnTripStartTime) {
        this.returnTripStartTime = returnTripStartTime;
    }

    public double getOriginalWaitTimeHours() {
        return originalWaitTimeHours;
    }

    public void setOriginalWaitTimeHours(double originalWaitTimeHours) {
        this.originalWaitTimeHours = originalWaitTimeHours;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
