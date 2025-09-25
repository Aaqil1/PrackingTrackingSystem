package com.pracking.parking;

import com.pracking.parking.dto.VehicleArrivalRequest;
import com.pracking.parking.entity.SlotSize;
import com.pracking.parking.service.ParkingManagementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ParkingManagementServiceTest {

    @Autowired
    private ParkingManagementService service;

    @Test
    void allocateArrivalFindsMatchingSlot() {
        VehicleArrivalRequest request = new VehicleArrivalRequest();
        request.setVehicleSize(SlotSize.MEDIUM);
        var slotDto = service.handleArrival(1L, request);
        assertNotNull(slotDto);
        assertEquals(SlotSize.MEDIUM, slotDto.getSize());
        assertTrue(slotDto.isOccupied());
    }
}
