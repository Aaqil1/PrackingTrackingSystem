package com.pracking.driver_collaboration.service;

import com.pracking.driver_collaboration.dto.DriverSwapPlanDto;
import com.pracking.driver_collaboration.dto.DriverSwapPlanRequest;
import com.pracking.driver_collaboration.dto.DriverSwapSummaryDto;
import com.pracking.driver_collaboration.entity.DriverSwapPlan;
import com.pracking.driver_collaboration.exception.ResourceNotFoundException;
import com.pracking.driver_collaboration.repository.DriverSwapPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        double saved = request.getSavedWaitTimeHours() > 0 ? request.getSavedWaitTimeHours() : 2.0;
        DriverSwapPlan plan = new DriverSwapPlan(request.getOriginBranch(), request.getDestinationBranch(),
                request.getDepartureDate(), request.getDriversInvolved(), saved,
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
        return new DriverSwapSummaryDto(plans.size(), totalSaved);
    }

    private DriverSwapPlanDto toDto(DriverSwapPlan plan) {
        return new DriverSwapPlanDto(plan.getId(), plan.getOriginBranch(), plan.getDestinationBranch(), plan.getDepartureDate(),
                plan.getDriversInvolved(), plan.getSavedWaitTimeHours(), plan.getNotes());
    }
}
