package com.pracking.allocation.service;

import com.pracking.allocation.dto.AutoAllocationResponse;
import com.pracking.allocation.dto.GoodsAllocationRequest;
import com.pracking.allocation.dto.VehicleDto;
import com.pracking.allocation.dto.VehicleRequest;
import com.pracking.allocation.entity.Vehicle;
import com.pracking.allocation.exception.BusinessException;
import com.pracking.allocation.exception.ResourceNotFoundException;
import com.pracking.allocation.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehicleAllocationService {

    private final VehicleRepository repository;

    public VehicleAllocationService(VehicleRepository repository) {
        this.repository = repository;
    }

    public VehicleDto onboard(VehicleRequest request) {
        Vehicle vehicle = new Vehicle(request.getRegistrationNumber(), request.getModel(), request.getMaxVolume(), request.getMaxQuantity());
        Vehicle saved = repository.save(vehicle);
        return toDto(saved);
    }

    public List<VehicleDto> listVehicles() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public VehicleDto getVehicle(Long id) {
        return repository.findById(id).map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
    }

    public AutoAllocationResponse autoAllocate(GoodsAllocationRequest request) {
        List<Vehicle> candidates = repository.findAll().stream()
                .filter(vehicle -> vehicle.getMaxVolume() >= request.getGoodsVolume())
                .filter(vehicle -> vehicle.getMaxQuantity() >= request.getGoodsQuantity())
                .sorted(Comparator.comparingDouble(Vehicle::getMaxVolume)
                        .thenComparingInt(Vehicle::getMaxQuantity))
                .collect(Collectors.toList());
        Vehicle selected = candidates.stream().findFirst()
                .orElseThrow(() -> new BusinessException("No vehicle matches goods dimensions"));
        String comment = String.format("Vehicle %s selected for volume %.1f and quantity %d", selected.getRegistrationNumber(),
                request.getGoodsVolume(), request.getGoodsQuantity());
        return new AutoAllocationResponse(toDto(selected), comment);
    }

    private VehicleDto toDto(Vehicle vehicle) {
        return new VehicleDto(vehicle.getId(), vehicle.getRegistrationNumber(), vehicle.getModel(), vehicle.getMaxVolume(), vehicle.getMaxQuantity());
    }
}
