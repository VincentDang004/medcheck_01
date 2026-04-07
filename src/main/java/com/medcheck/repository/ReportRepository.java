package com.medcheck.repository;

import com.medcheck.model.Report;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    long countByReporterUsernameAndReportDateAfter(String reporterUsername, LocalDateTime after);

    List<Report> findByStatus(String status);
}
