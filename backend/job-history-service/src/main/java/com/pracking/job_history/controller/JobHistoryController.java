package com.pracking.job_history.controller;

import com.pracking.job_history.dto.JobRecordDto;
import com.pracking.job_history.dto.JobRecordRequest;
import com.pracking.job_history.service.JobHistoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobHistoryController {

    private final JobHistoryService service;

    public JobHistoryController(JobHistoryService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobRecordDto create(@Valid @RequestBody JobRecordRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<JobRecordDto> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public JobRecordDto get(@PathVariable Long id) {
        return service.get(id);
    }
}
