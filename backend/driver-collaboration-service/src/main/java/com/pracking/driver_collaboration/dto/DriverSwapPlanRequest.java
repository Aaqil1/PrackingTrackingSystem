package com.pracking.driver_collaboration.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class DriverSwapPlanRequest {
    @NotBlank
    private String originBranch;
    @NotBlank
    private String destinationBranch;
    @FutureOrPresent
    private LocalDate departureDate;
    @NotBlank
    private String driversInvolved;
    @Min(0)
    private double savedWaitTimeHours;
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

    public double getSavedWaitTimeHours() {
        return savedWaitTimeHours;
    }

    public void setSavedWaitTimeHours(double savedWaitTimeHours) {
        this.savedWaitTimeHours = savedWaitTimeHours;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
