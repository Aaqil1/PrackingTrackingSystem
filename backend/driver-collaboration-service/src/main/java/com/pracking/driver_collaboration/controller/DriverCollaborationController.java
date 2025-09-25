package com.pracking.driver_collaboration.controller;

import com.pracking.driver_collaboration.dto.DriverSwapPlanDto;
import com.pracking.driver_collaboration.dto.DriverSwapPlanRequest;
import com.pracking.driver_collaboration.dto.DriverSwapSummaryDto;
import com.pracking.driver_collaboration.service.DriverCollaborationService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/driver-swaps")
public class DriverCollaborationController {

    private final DriverCollaborationService service;

    public DriverCollaborationController(DriverCollaborationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverSwapPlanDto create(@Valid @RequestBody DriverSwapPlanRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<DriverSwapPlanDto> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public DriverSwapPlanDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/summary")
    public DriverSwapSummaryDto summary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return service.summary(start, end);
    }
}
