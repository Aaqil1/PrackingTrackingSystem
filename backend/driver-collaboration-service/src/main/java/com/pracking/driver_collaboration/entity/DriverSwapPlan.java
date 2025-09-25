package com.pracking.driver_collaboration.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class DriverSwapPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originBranch;
    private String destinationBranch;
    private LocalDate departureDate;
    private String driversInvolved;
    private double savedWaitTimeHours;
    private String notes;

    public DriverSwapPlan() {
    }

    public DriverSwapPlan(String originBranch, String destinationBranch, LocalDate departureDate, String driversInvolved, double savedWaitTimeHours, String notes) {
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
