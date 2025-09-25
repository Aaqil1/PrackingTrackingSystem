package com.pracking.job_history.service;

import com.pracking.job_history.dto.JobRecordDto;
import com.pracking.job_history.dto.JobRecordRequest;
import com.pracking.job_history.entity.JobRecord;
import com.pracking.job_history.exception.ResourceNotFoundException;
import com.pracking.job_history.repository.JobRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class JobHistoryService {

    private final JobRecordRepository repository;

    public JobHistoryService(JobRecordRepository repository) {
        this.repository = repository;
    }

    public JobRecordDto create(JobRecordRequest request) {
        JobRecord record = new JobRecord(request.getVehicleId(), request.getDescription(), request.getGoodsVolume(), request.getJobDate(), request.getStatus());
        JobRecord saved = repository.save(record);
        return toDto(saved);
    }

    public List<JobRecordDto> list() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public JobRecordDto get(Long id) {
        return repository.findById(id).map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
    }

    private JobRecordDto toDto(JobRecord record) {
        return new JobRecordDto(record.getId(), record.getVehicleId(), record.getDescription(), record.getGoodsVolume(), record.getJobDate(), record.getStatus());
    }
}
