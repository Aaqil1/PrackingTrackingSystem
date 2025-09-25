package com.pracking.parking.service;

import com.pracking.parking.dto.*;
import com.pracking.parking.entity.ParkingLot;
import com.pracking.parking.entity.ParkingSlot;
import com.pracking.parking.entity.SlotSize;
import com.pracking.parking.exception.BusinessException;
import com.pracking.parking.exception.ResourceNotFoundException;
import com.pracking.parking.repository.ParkingLotRepository;
import com.pracking.parking.repository.ParkingSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParkingManagementService {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSlotRepository parkingSlotRepository;

    public ParkingManagementService(ParkingLotRepository parkingLotRepository, ParkingSlotRepository parkingSlotRepository) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingSlotRepository = parkingSlotRepository;
    }

    public ParkingLotDto createLot(CreateParkingLotRequest request) {
        ParkingLot lot = new ParkingLot(request.getName(), request.getCity(), request.getArea());
        lot.addSlot(new ParkingSlot("S1", SlotSize.SMALL, false));
        lot.addSlot(new ParkingSlot("M1", SlotSize.MEDIUM, false));
        lot.addSlot(new ParkingSlot("L1", SlotSize.LARGE, false));
        ParkingLot saved = parkingLotRepository.save(lot);
        return toDto(saved);
    }

    public List<ParkingLotDto> listLots() {
        return parkingLotRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ParkingSlotDto handleArrival(Long lotId, VehicleArrivalRequest request) {
        ParkingLot lot = parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new ResourceNotFoundException("Parking lot not found"));
        List<ParkingSlot> available = parkingSlotRepository.findByParkingLotIdAndOccupiedFalse(lot.getId());
        SlotSize vehicleSize = request.getVehicleSize();
        ParkingSlot allocated = available.stream()
                .filter(slot -> slot.getSize().canFit(vehicleSize))
                .sorted(Comparator.comparingInt(slot -> slot.getSize().ordinal()))
                .findFirst()
                .orElseThrow(() -> new BusinessException("No available slot for size " + vehicleSize));
        allocated.setOccupied(true);
        parkingSlotRepository.save(allocated);
        return new ParkingSlotDto(allocated.getId(), allocated.getLabel(), allocated.getSize(), allocated.isOccupied());
    }

    public void handleDeparture(Long lotId, VehicleDepartureRequest request) {
        ParkingSlot slot = parkingSlotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found"));
        if (!slot.getParkingLot().getId().equals(lotId)) {
            throw new BusinessException("Slot does not belong to the specified lot");
        }
        slot.setOccupied(false);
        parkingSlotRepository.save(slot);
    }

    public ParkingPredictionResponse predict(Long lotId) {
        ParkingLot lot = parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new ResourceNotFoundException("Parking lot not found"));
        double occupancy = occupancyRate(lot.getId());
        boolean needMore = occupancy >= 0.75;
        return new ParkingPredictionResponse(lot.getId(), occupancy, needMore);
    }

    private ParkingLotDto toDto(ParkingLot lot) {
        double occupancy = occupancyRate(lot.getId());
        boolean needMore = occupancy >= 0.75;
        List<ParkingSlotDto> slots = parkingSlotRepository.findByParkingLotId(lot.getId()).stream()
                .map(slot -> new ParkingSlotDto(slot.getId(), slot.getLabel(), slot.getSize(), slot.isOccupied()))
                .collect(Collectors.toList());
        return new ParkingLotDto(lot.getId(), lot.getName(), lot.getCity(), lot.getArea(), occupancy, needMore, slots);
    }

    private double occupancyRate(Long lotId) {
        long total = parkingSlotRepository.countByParkingLotId(lotId);
        if (total == 0) {
            return 0.0;
        }
        long occupied = parkingSlotRepository.countByParkingLotIdAndOccupiedTrue(lotId);
        return (double) occupied / total;
    }
}
