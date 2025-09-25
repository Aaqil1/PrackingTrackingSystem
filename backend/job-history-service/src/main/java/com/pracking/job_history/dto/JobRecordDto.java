package com.pracking.job_history.dto;

import java.time.LocalDate;

public class JobRecordDto {
    private Long id;
    private String vehicleId;
    private String description;
    private double goodsVolume;
    private LocalDate jobDate;
    private String status;

    public JobRecordDto(Long id, String vehicleId, String description, double goodsVolume, LocalDate jobDate, String status) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.description = description;
        this.goodsVolume = goodsVolume;
        this.jobDate = jobDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getDescription() {
        return description;
    }

    public double getGoodsVolume() {
        return goodsVolume;
    }

    public LocalDate getJobDate() {
        return jobDate;
    }

    public String getStatus() {
        return status;
    }
}
