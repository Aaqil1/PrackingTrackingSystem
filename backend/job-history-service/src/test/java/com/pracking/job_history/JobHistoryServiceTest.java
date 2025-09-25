package com.pracking.job_history;

import com.pracking.job_history.dto.JobRecordRequest;
import com.pracking.job_history.service.JobHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class JobHistoryServiceTest {

    @Autowired
    private JobHistoryService service;

    @Test
    void createStoresJob() {
        JobRecordRequest request = new JobRecordRequest();
        request.setVehicleId("MH12AB9999");
        request.setDescription("Test job");
        request.setGoodsVolume(40);
        request.setJobDate(LocalDate.now());
        request.setStatus("PLANNED");
        var dto = service.create(request);
        assertNotNull(dto.getId());
        assertEquals("Test job", dto.getDescription());
    }
}
