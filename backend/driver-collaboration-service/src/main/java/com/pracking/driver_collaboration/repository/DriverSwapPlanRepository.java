package com.pracking.driver_collaboration.repository;

import com.pracking.driver_collaboration.entity.DriverSwapPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DriverSwapPlanRepository extends JpaRepository<DriverSwapPlan, Long> {
    List<DriverSwapPlan> findByDepartureDateBetween(LocalDate start, LocalDate end);
}
