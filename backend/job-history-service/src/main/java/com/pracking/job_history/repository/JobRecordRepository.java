package com.pracking.job_history.repository;

import com.pracking.job_history.entity.JobRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRecordRepository extends JpaRepository<JobRecord, Long> {
}
