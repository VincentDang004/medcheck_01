package com.medcheck.service;

import com.medcheck.model.Medicine;
import com.medcheck.model.Report;
import com.medcheck.model.User;
import com.medcheck.repository.MedicineRepository;
import com.medcheck.repository.ReportRepository;
import com.medcheck.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private static final int SPAM_REPORT_LIMIT = 4;

    private final MedicineRepository repository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportService(
            MedicineRepository repository,
            ReportRepository reportRepository,
            UserRepository userRepository) {
        this.repository = repository;
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    public ReportSubmissionResult saveReport(Report report, String username) {
        if (username == null || username.isBlank()) {
            reportRepository.save(report);
            return ReportSubmissionResult.SAVED;
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            report.setReporterUsername(username);
            reportRepository.save(report);
            return ReportSubmissionResult.SAVED;
        }

        User user = optionalUser.get();
        if (user.isBlocked()) {
            return ReportSubmissionResult.USER_BLOCKED;
        }

        report.setReporterUsername(username);
        reportRepository.save(report);

        user.setReportCount(user.getReportCount() + 1);
        user.setLastReportAt(LocalDateTime.now());

        long recentReports = reportRepository.countByReporterUsernameAndReportDateAfter(
                username, LocalDateTime.now().minusMinutes(10));
        if (recentReports >= SPAM_REPORT_LIMIT) {
            user.setBlocked(true);
            user.setSpamStrike(user.getSpamStrike() + 1);
            userRepository.save(user);
            return ReportSubmissionResult.BLOCKED_FOR_SPAM;
        }

        userRepository.save(user);
        return ReportSubmissionResult.SAVED;
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public List<Report> getVerifiedReports() {
        return reportRepository.findByStatus("VERIFIED");
    }

    public void verifyFakeMedicine(Long reportId) {
        Report report = reportRepository.findById(reportId).orElse(null);
        if (report != null) {
            report.setStatus("VERIFIED");
            report.setProcessedDate(LocalDateTime.now());
            reportRepository.save(report);

            if (report.getMedicineId() != null) {
                Medicine medicine = repository.findById(report.getMedicineId()).orElse(null);
                if (medicine != null) {
                    medicine.setRecalled(true);
                    repository.save(medicine);
                }
            }
        }
    }

    public void rejectReport(Long reportId) {
        Report report = reportRepository.findById(reportId).orElse(null);
        if (report != null) {
            report.setStatus("REJECTED");
            report.setProcessedDate(LocalDateTime.now());
            reportRepository.save(report);
        }
    }

    public enum ReportSubmissionResult {
        SAVED,
        USER_BLOCKED,
        BLOCKED_FOR_SPAM
    }
}
