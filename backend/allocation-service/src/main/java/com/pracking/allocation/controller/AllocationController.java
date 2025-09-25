package com.pracking.allocation.controller;

import com.pracking.allocation.dto.AutoAllocationResponse;
import com.pracking.allocation.dto.GoodsAllocationRequest;
import com.pracking.allocation.dto.VehicleDto;
import com.pracking.allocation.dto.VehicleRequest;
import com.pracking.allocation.service.VehicleAllocationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/allocation")
public class AllocationController {

    private final VehicleAllocationService service;

    public AllocationController(VehicleAllocationService service) {
        this.service = service;
    }

    @PostMapping("/vehicles")
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleDto onboard(@Valid @RequestBody VehicleRequest request) {
        return service.onboard(request);
    }

    @GetMapping("/vehicles")
    public List<VehicleDto> vehicles() {
        return service.listVehicles();
    }

    @GetMapping("/vehicles/{id}")
    public VehicleDto vehicle(@PathVariable Long id) {
        return service.getVehicle(id);
    }

    @PostMapping("/auto")
    public AutoAllocationResponse autoAllocate(@Valid @RequestBody GoodsAllocationRequest request) {
        return service.autoAllocate(request);
    }
}
