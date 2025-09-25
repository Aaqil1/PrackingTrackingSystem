package com.pracking.parking.repository;

import com.pracking.parking.entity.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    List<ParkingSlot> findByParkingLotIdAndOccupiedFalse(Long parkingLotId);
    List<ParkingSlot> findByParkingLotId(Long parkingLotId);
    long countByParkingLotIdAndOccupiedTrue(Long parkingLotId);
    long countByParkingLotId(Long parkingLotId);
}
