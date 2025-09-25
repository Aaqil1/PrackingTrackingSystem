package com.pracking.parking.controller;

import com.pracking.parking.dto.*;
import com.pracking.parking.service.ParkingManagementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingManagementService service;

    public ParkingController(ParkingManagementService service) {
        this.service = service;
    }

    @PostMapping("/lots")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingLotDto createLot(@Valid @RequestBody CreateParkingLotRequest request) {
        return service.createLot(request);
    }

    @GetMapping("/lots")
    public List<ParkingLotDto> listLots() {
        return service.listLots();
    }

    @PostMapping("/lots/{lotId}/arrival")
    public ParkingSlotDto allocateOnArrival(@PathVariable Long lotId, @Valid @RequestBody VehicleArrivalRequest request) {
        return service.handleArrival(lotId, request);
    }

    @PostMapping("/lots/{lotId}/departure")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void releaseOnDeparture(@PathVariable Long lotId, @Valid @RequestBody VehicleDepartureRequest request) {
        service.handleDeparture(lotId, request);
    }

    @GetMapping("/lots/{lotId}/prediction")
    public ParkingPredictionResponse predict(@PathVariable Long lotId) {
        return service.predict(lotId);
    }
}
