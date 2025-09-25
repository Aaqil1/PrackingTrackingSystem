package com.pracking.telemetry.dto;

public class ConsumptionAnomalyDto {
    private Long recordId;
    private String vehicleId;
    private String metricName;
    private int expectedDays;
    private int actualDays;
    private int deviationDays;

    public ConsumptionAnomalyDto(Long recordId, String vehicleId, String metricName, int expectedDays, int actualDays, int deviationDays) {
        this.recordId = recordId;
        this.vehicleId = vehicleId;
        this.metricName = metricName;
        this.expectedDays = expectedDays;
        this.actualDays = actualDays;
        this.deviationDays = deviationDays;
    }

    public Long getRecordId() {
        return recordId;
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

    public int getDeviationDays() {
        return deviationDays;
    }
}
