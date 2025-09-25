package com.pracking.job_history.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class JobRecordRequest {
    @NotBlank
    private String vehicleId;
    @NotBlank
    private String description;
    @Min(0)
    private double goodsVolume;
    @NotNull
    private LocalDate jobDate;
    @NotBlank
    private String status;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getGoodsVolume() {
        return goodsVolume;
    }

    public void setGoodsVolume(double goodsVolume) {
        this.goodsVolume = goodsVolume;
    }

    public LocalDate getJobDate() {
        return jobDate;
    }

    public void setJobDate(LocalDate jobDate) {
        this.jobDate = jobDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
