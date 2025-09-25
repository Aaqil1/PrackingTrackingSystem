package com.pracking.telemetry.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class ConsumptionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehicleId;
    private String metricName;
    private int expectedDays;
    private int actualDays;
    private LocalDate lastCheckedDate;

    public ConsumptionRecord() {
    }

    public ConsumptionRecord(String vehicleId, String metricName, int expectedDays, int actualDays, LocalDate lastCheckedDate) {
        this.vehicleId = vehicleId;
        this.metricName = metricName;
        this.expectedDays = expectedDays;
        this.actualDays = actualDays;
        this.lastCheckedDate = lastCheckedDate;
    }

    public Long getId() {
        return id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getMetricName() {
        return metricName;
    }

    public int getExpectedDays() {
        return expectedDays;
    }

    public int getActualDays() {
        return actualDays;
    }

    public LocalDate getLastCheckedDate() {
        return lastCheckedDate;
    }
}
