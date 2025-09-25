package com.pracking.allocation;

import com.pracking.allocation.dto.GoodsAllocationRequest;
import com.pracking.allocation.service.VehicleAllocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class VehicleAllocationServiceTest {

    @Autowired
    private VehicleAllocationService service;

    @Test
    void autoAllocateSelectsVehicle() {
        GoodsAllocationRequest request = new GoodsAllocationRequest();
        request.setGoodsVolume(90);
        request.setGoodsQuantity(100);
        var response = service.autoAllocate(request);
        assertNotNull(response.getVehicle());
        assertTrue(response.getAllocationComment().contains("Vehicle"));
    }
}
