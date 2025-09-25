package com.pracking.driver_collaboration.dto;

import java.time.LocalDate;

public class DriverSwapPlanDto {
    private Long id;
    private String originBranch;
    private String destinationBranch;
    private LocalDate departureDate;
    private String driversInvolved;
    private double savedWaitTimeHours;
    private String notes;

    public DriverSwapPlanDto(Long id, String originBranch, String destinationBranch, LocalDate departureDate, String driversInvolved, double savedWaitTimeHours, String notes) {
        this.id = id;
        this.originBranch = originBranch;
        this.destinationBranch = destinationBranch;
        this.departureDate = departureDate;
        this.driversInvolved = driversInvolved;
        this.savedWaitTimeHours = savedWaitTimeHours;
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

    public double getSavedWaitTimeHours() {
        return savedWaitTimeHours;
    }

    public String getNotes() {
        return notes;
    }
}
