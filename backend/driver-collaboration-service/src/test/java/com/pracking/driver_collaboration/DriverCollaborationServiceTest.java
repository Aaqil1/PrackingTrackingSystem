package com.pracking.driver_collaboration;

import com.pracking.driver_collaboration.dto.DriverSwapPlanRequest;
import com.pracking.driver_collaboration.service.DriverCollaborationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DriverCollaborationServiceTest {

    @Autowired
    private DriverCollaborationService service;

    @Test
    void createPlanCalculatesSavedTime() {
        DriverSwapPlanRequest request = new DriverSwapPlanRequest();
        request.setOriginBranch("Central Hub");
        request.setDestinationBranch("East Branch");
        request.setDepartureDate(LocalDate.now());
        request.setDriversInvolved("Driver E, Driver F");
        request.setShiftStartTime(LocalTime.of(6, 0));
        request.setShiftEndTime(LocalTime.of(14, 0));
        request.setReturnTripStartTime(LocalTime.of(14, 30));
        request.setOriginalWaitTimeHours(4);
        request.setHourlyRate(45);
        var plan = service.create(request);
        assertTrue(plan.getSavedWaitTimeHours() > 0);
        assertTrue(plan.getSavedWaitCost() > 0);
    }
}
