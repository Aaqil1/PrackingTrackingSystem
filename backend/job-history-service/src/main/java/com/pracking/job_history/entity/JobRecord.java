package com.pracking.job_history.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class JobRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehicleId;
    private String description;
    private double goodsVolume;
    private LocalDate jobDate;
    private String status;

    public JobRecord() {
    }

    public JobRecord(String vehicleId, String description, double goodsVolume, LocalDate jobDate, String status) {
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

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGoodsVolume(double goodsVolume) {
        this.goodsVolume = goodsVolume;
    }

    public void setJobDate(LocalDate jobDate) {
        this.jobDate = jobDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
