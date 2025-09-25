package com.pracking.telemetry;

import com.pracking.telemetry.service.ConsumptionMonitoringService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ConsumptionMonitoringServiceTest {

    @Autowired
    private ConsumptionMonitoringService service;

    @Test
    void anomaliesDetectsEarlyConsumption() {
        var anomalies = service.anomalies();
        assertFalse(anomalies.isEmpty());
        assertEquals("Coolant", anomalies.get(0).getMetricName());
    }
}
