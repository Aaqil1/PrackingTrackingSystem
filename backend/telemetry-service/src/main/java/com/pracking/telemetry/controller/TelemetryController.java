package com.pracking.telemetry.controller;

import com.pracking.telemetry.dto.ConsumptionAnomalyDto;
import com.pracking.telemetry.dto.ConsumptionRecordDto;
import com.pracking.telemetry.dto.ConsumptionRecordRequest;
import com.pracking.telemetry.service.ConsumptionMonitoringService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telemetry")
public class TelemetryController {

    private final ConsumptionMonitoringService service;

    public TelemetryController(ConsumptionMonitoringService service) {
        this.service = service;
    }

    @PostMapping("/records")
    @ResponseStatus(HttpStatus.CREATED)
    public ConsumptionRecordDto create(@Valid @RequestBody ConsumptionRecordRequest request) {
        return service.create(request);
    }

    @GetMapping("/records")
    public List<ConsumptionRecordDto> list() {
        return service.list();
    }

    @GetMapping("/records/{id}")
    public ConsumptionRecordDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/records/vehicle/{vehicleId}")
    public List<ConsumptionRecordDto> byVehicle(@PathVariable String vehicleId) {
        return service.byVehicle(vehicleId);
    }

    @GetMapping("/anomalies")
    public List<ConsumptionAnomalyDto> anomalies() {
        return service.anomalies();
    }
}
