package com.pracking.telemetry.repository;

import com.pracking.telemetry.entity.ConsumptionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsumptionRecordRepository extends JpaRepository<ConsumptionRecord, Long> {
    List<ConsumptionRecord> findByVehicleId(String vehicleId);
}
