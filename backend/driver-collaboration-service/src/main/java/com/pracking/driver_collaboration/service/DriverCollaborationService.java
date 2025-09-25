package com.pracking.driver_collaboration.service;

import com.pracking.driver_collaboration.dto.DriverSwapPlanDto;
import com.pracking.driver_collaboration.dto.DriverSwapPlanRequest;
import com.pracking.driver_collaboration.dto.DriverSwapSummaryDto;
import com.pracking.driver_collaboration.entity.DriverSwapPlan;
import com.pracking.driver_collaboration.exception.ResourceNotFoundException;
import com.pracking.driver_collaboration.repository.DriverSwapPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DriverCollaborationService {

    private final DriverSwapPlanRepository repository;

    public DriverCollaborationService(DriverSwapPlanRepository repository) {
        this.repository = repository;
    }

    public DriverSwapPlanDto create(DriverSwapPlanRequest request) {
        double originalWait = request.getOriginalWaitTimeHours() > 0 ? request.getOriginalWaitTimeHours() : 4.0;
        LocalTime end = request.getShiftEndTime();
        LocalTime returnStart = request.getReturnTripStartTime();
        double actualWait = Math.max(0,
                Duration.between(end, returnStart).toMinutes() / 60.0);
        double savedHours = Math.max(0, originalWait - actualWait);
        double hourlyRate = request.getHourlyRate() > 0 ? request.getHourlyRate() : 20.0;
        double savedCost = savedHours * hourlyRate;

        DriverSwapPlan plan = new DriverSwapPlan(request.getOriginBranch(), request.getDestinationBranch(),
                request.getDepartureDate(), request.getDriversInvolved(), request.getShiftStartTime(),
                request.getShiftEndTime(), request.getReturnTripStartTime(), savedHours, savedCost,
                request.getNotes() == null ? "Auto-calculated swap plan" : request.getNotes());
        return toDto(repository.save(plan));
    }

    public List<DriverSwapPlanDto> list() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public DriverSwapPlanDto get(Long id) {
        return repository.findById(id).map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));
    }

    public DriverSwapSummaryDto summary(LocalDate start, LocalDate end) {
        List<DriverSwapPlan> plans = repository.findByDepartureDateBetween(start, end);
        double totalSaved = plans.stream().mapToDouble(DriverSwapPlan::getSavedWaitTimeHours).sum();
        double totalSavedCost = plans.stream().mapToDouble(DriverSwapPlan::getSavedWaitCost).sum();
        return new DriverSwapSummaryDto(plans.size(), totalSaved, totalSavedCost);
    }

    private DriverSwapPlanDto toDto(DriverSwapPlan plan) {
        return new DriverSwapPlanDto(plan.getId(), plan.getOriginBranch(), plan.getDestinationBranch(), plan.getDepartureDate(),
                plan.getDriversInvolved(), plan.getShiftStartTime(), plan.getShiftEndTime(), plan.getReturnTripStartTime(),
                plan.getSavedWaitTimeHours(), plan.getSavedWaitCost(), plan.getNotes());
    }
}
