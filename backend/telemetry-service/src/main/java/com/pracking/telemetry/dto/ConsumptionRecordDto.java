package com.pracking.telemetry.dto;

import java.time.LocalDate;

public class ConsumptionRecordDto {
    private Long id;
    private String vehicleId;
    private String metricName;
    private int expectedDays;
    private int actualDays;
    private LocalDate lastCheckedDate;
    private boolean anomaly;

    public ConsumptionRecordDto(Long id, String vehicleId, String metricName, int expectedDays, int actualDays, LocalDate lastCheckedDate, boolean anomaly) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.metricName = metricName;
        this.expectedDays = expectedDays;
        this.actualDays = actualDays;
        this.lastCheckedDate = lastCheckedDate;
        this.anomaly = anomaly;
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

    public boolean isAnomaly() {
        return anomaly;
    }
}
