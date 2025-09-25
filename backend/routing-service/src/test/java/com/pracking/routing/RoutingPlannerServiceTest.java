package com.pracking.routing;

import com.pracking.routing.dto.RoutingRequest;
import com.pracking.routing.service.RoutingPlannerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoutingPlannerServiceTest {

    @Autowired
    private RoutingPlannerService service;

    @Test
    void suggestReturnsClosestOption() {
        RoutingRequest request = new RoutingRequest();
        request.setCurrentCity("Metropolis");
        request.setVehicleSize("medium");
        request.setMaximumDistanceKm(10);
        var suggestion = service.suggest(request);
        assertEquals("Central Hub", suggestion.getLotName());
    }
}
