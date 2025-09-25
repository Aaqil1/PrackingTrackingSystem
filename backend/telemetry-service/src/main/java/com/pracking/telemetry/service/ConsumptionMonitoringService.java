package com.pracking.telemetry.service;

import com.pracking.telemetry.dto.ConsumptionAnomalyDto;
import com.pracking.telemetry.dto.ConsumptionRecordDto;
import com.pracking.telemetry.dto.ConsumptionRecordRequest;
import com.pracking.telemetry.entity.ConsumptionRecord;
import com.pracking.telemetry.exception.ResourceNotFoundException;
import com.pracking.telemetry.repository.ConsumptionRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConsumptionMonitoringService {

    private final ConsumptionRecordRepository repository;

    public ConsumptionMonitoringService(ConsumptionRecordRepository repository) {
        this.repository = repository;
    }

    public ConsumptionRecordDto create(ConsumptionRecordRequest request) {
        ConsumptionRecord record = new ConsumptionRecord(request.getVehicleId(), request.getMetricName(),
                request.getExpectedDays(), request.getActualDays(), request.getLastCheckedDate());
        return toDto(repository.save(record));
    }

    public List<ConsumptionRecordDto> list() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<ConsumptionRecordDto> byVehicle(String vehicleId) {
        return repository.findByVehicleId(vehicleId).stream().map(this::toDto).collect(Collectors.toList());
    }

    public ConsumptionRecordDto get(Long id) {
        return repository.findById(id).map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));
    }

    public List<ConsumptionAnomalyDto> anomalies() {
        return repository.findAll().stream()
                .filter(this::isAnomaly)
                .map(record -> new ConsumptionAnomalyDto(record.getId(), record.getVehicleId(), record.getMetricName(),
                        record.getExpectedDays(), record.getActualDays(), record.getExpectedDays() - record.getActualDays()))
                .collect(Collectors.toList());
    }

    private boolean isAnomaly(ConsumptionRecord record) {
        return record.getActualDays() < Math.round(record.getExpectedDays() * 0.8);
    }

    private ConsumptionRecordDto toDto(ConsumptionRecord record) {
        boolean anomaly = isAnomaly(record);
        return new ConsumptionRecordDto(record.getId(), record.getVehicleId(), record.getMetricName(),
                record.getExpectedDays(), record.getActualDays(), record.getLastCheckedDate(), anomaly);
    }
}
