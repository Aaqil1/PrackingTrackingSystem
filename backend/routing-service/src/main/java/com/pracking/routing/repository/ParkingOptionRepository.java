package com.pracking.routing.repository;

import com.pracking.routing.entity.ParkingOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingOptionRepository extends JpaRepository<ParkingOption, Long> {
    List<ParkingOption> findByCity(String city);
    List<ParkingOption> findByArea(String area);
}
