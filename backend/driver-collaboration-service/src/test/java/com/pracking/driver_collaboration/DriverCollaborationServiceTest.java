package com.pracking.driver_collaboration;

import com.pracking.driver_collaboration.dto.DriverSwapPlanRequest;
import com.pracking.driver_collaboration.service.DriverCollaborationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

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
        request.setSavedWaitTimeHours(0);
        var plan = service.create(request);
        assertTrue(plan.getSavedWaitTimeHours() >= 2.0);
    }
}
