package com.medcheck.controller;

import com.medcheck.model.Medicine;
import com.medcheck.model.Report;
import com.medcheck.service.MedicineService;
import com.medcheck.service.ReportService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MedicineController {

    private final MedicineService service;
    private final ReportService reportService;

    public MedicineController(MedicineService service, ReportService reportService) {
        this.service = service;
        this.reportService = reportService;
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        model.addAttribute("medicines", service.search(keyword));
        return "user/index";
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("medicines", service.getPublicMedicines());
        return "user/index";
    }

    @GetMapping("/medicine/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Medicine medicine = service.getPublicMedicine(id);
        if (medicine == null) {
            return "redirect:/?medicineUnavailable";
        }

        model.addAttribute("medicine", medicine);
        return "user/detail";
    }

    @GetMapping("/report-new")
    public String reportNewForm(@RequestParam(required = false) Long medicineId, Model model) {
        Medicine medicine = medicineId != null ? service.getPublicMedicine(medicineId) : null;
        model.addAttribute("medicine", medicine != null ? medicine : new Medicine());
        return "user/report-form";
    }

    @PostMapping("/report/submit")
    public String submitReport(@ModelAttribute Report report, Authentication authentication) {
        String username = authentication != null ? authentication.getName() : null;
        ReportService.ReportSubmissionResult result = reportService.saveReport(report, username);

        if (result == ReportService.ReportSubmissionResult.USER_BLOCKED) {
            return "redirect:/report-new?blocked=true";
        }
        if (result == ReportService.ReportSubmissionResult.BLOCKED_FOR_SPAM) {
            return "redirect:/login?blocked=true";
        }

        return "redirect:/?success=true";
    }

    @GetMapping("/alerts")
    public String publicAlerts(Model model) {
        model.addAttribute("reports", reportService.getVerifiedReports());
        return "user/alerts";
    }
}
