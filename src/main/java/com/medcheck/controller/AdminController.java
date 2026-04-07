package com.medcheck.controller;

import com.medcheck.model.Medicine;
import com.medcheck.model.Report;
import com.medcheck.model.Category;
import com.medcheck.model.Faq;
import com.medcheck.model.Hotline;
import com.medcheck.service.CategoryService;
import com.medcheck.service.FaqService;
import com.medcheck.service.HotlineService;
import com.medcheck.service.MedicineService;
import com.medcheck.service.ReportService;
import com.medcheck.service.UserService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final MedicineService service;
    private final ReportService reportService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final FaqService faqService;
    private final HotlineService hotlineService;

    public AdminController(
            MedicineService service,
            ReportService reportService,
            UserService userService,
            CategoryService categoryService,
            FaqService faqService,
            HotlineService hotlineService) {
        this.service = service;
        this.reportService = reportService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.faqService = faqService;
        this.hotlineService = hotlineService;
    }

    @GetMapping("/admin/reports")
    public String adminReports(Model model) {
        model.addAttribute("reports", reportService.getAllReports());
        return "admin/admin-reports";
    }

    @PostMapping("/admin/reports/verify/{id}")
    public String verifyReport(@PathVariable Long id) {
        reportService.verifyFakeMedicine(id);
        return "redirect:/admin/reports";
    }

    @PostMapping("/admin/reports/reject/{id}")
    public String rejectReport(@PathVariable Long id) {
        reportService.rejectReport(id);
        return "redirect:/admin/reports";
    }

    @GetMapping("/admin/medicines")
    public String manageMedicines(Model model) {
        model.addAttribute("medicines", service.getAllMedicines());
        return "admin/admin-medicines";
    }

    @GetMapping("/admin/categories")
    public String manageCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategoriesAdmin());
        return "admin/admin-categories";
    }

    @GetMapping("/admin/categories/add")
    public String addCategoryForm(Model model) {
        Category category = new Category();
        category.setStatus(Boolean.TRUE);
        model.addAttribute("category", category);
        return "admin/category-form";
    }

    @GetMapping("/admin/categories/edit/{id}")
    public String editCategoryForm(@PathVariable Integer id, Model model) {
        Category category = categoryService.getCategoryAdmin(id);
        if (category == null) {
            return "redirect:/admin/categories?notFound=true";
        }
        model.addAttribute("category", category);
        return "admin/category-form";
    }

    @PostMapping("/admin/categories/save")
    public String saveCategory(@ModelAttribute Category category) {
        if (category.getStatus() == null) {
            category.setStatus(Boolean.TRUE);
        }
        categoryService.saveCategory(category);
        return "redirect:/admin/categories";
    }

    @PostMapping("/admin/categories/toggle/{id}")
    public String toggleCategory(@PathVariable Integer id) {
        categoryService.toggleStatus(id);
        return "redirect:/admin/categories";
    }

    @PostMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable Integer id, @RequestParam(required = false) boolean hard) {
        categoryService.deactivate(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/faqs")
    public String manageFaqs(Model model) {
        model.addAttribute("faqs", faqService.getAllFaqsAdmin());
        return "admin/admin-faqs";
    }

    @GetMapping("/admin/faqs/add")
    public String addFaqForm(Model model) {
        Faq faq = new Faq();
        faq.setStatus(Boolean.TRUE);
        model.addAttribute("faq", faq);
        return "admin/faq-form";
    }

    @GetMapping("/admin/faqs/edit/{id}")
    public String editFaqForm(@PathVariable Long id, Model model) {
        Faq faq = faqService.getFaq(id);
        if (faq == null) {
            return "redirect:/admin/faqs?notFound=true";
        }
        model.addAttribute("faq", faq);
        return "admin/faq-form";
    }

    @PostMapping("/admin/faqs/save")
    public String saveFaq(@ModelAttribute Faq faq) {
        if (faq.getStatus() == null) {
            faq.setStatus(Boolean.TRUE);
        }
        if (faq.getDisplayOrder() == null) {
            faq.setDisplayOrder(0);
        }
        faqService.saveFaq(faq);
        return "redirect:/admin/faqs";
    }

    @PostMapping("/admin/faqs/toggle/{id}")
    public String toggleFaq(@PathVariable Long id) {
        faqService.toggleStatus(id);
        return "redirect:/admin/faqs";
    }

    @PostMapping("/admin/faqs/delete/{id}")
    public String deleteFaq(@PathVariable Long id) {
        faqService.deactivate(id);
        return "redirect:/admin/faqs";
    }

    @GetMapping("/admin/hotlines")
    public String manageHotlines(Model model) {
        model.addAttribute("hotlines", hotlineService.getAllHotlinesAdmin());
        return "admin/admin-hotlines";
    }

    @GetMapping("/admin/hotlines/add")
    public String addHotlineForm(Model model) {
        Hotline hotline = new Hotline();
        hotline.setStatus(Boolean.TRUE);
        model.addAttribute("hotline", hotline);
        return "admin/hotline-form";
    }

    @GetMapping("/admin/hotlines/edit/{id}")
    public String editHotlineForm(@PathVariable Long id, Model model) {
        Hotline hotline = hotlineService.getHotline(id);
        if (hotline == null) {
            return "redirect:/admin/hotlines?notFound=true";
        }
        model.addAttribute("hotline", hotline);
        return "admin/hotline-form";
    }

    @PostMapping("/admin/hotlines/save")
    public String saveHotline(@ModelAttribute Hotline hotline) {
        if (hotline.getStatus() == null) {
            hotline.setStatus(Boolean.TRUE);
        }
        if (hotline.getDisplayOrder() == null) {
            hotline.setDisplayOrder(0);
        }
        hotlineService.saveHotline(hotline);
        return "redirect:/admin/hotlines";
    }

    @PostMapping("/admin/hotlines/toggle/{id}")
    public String toggleHotline(@PathVariable Long id) {
        hotlineService.toggleStatus(id);
        return "redirect:/admin/hotlines";
    }

    @PostMapping("/admin/hotlines/delete/{id}")
    public String deleteHotline(@PathVariable Long id) {
        hotlineService.deactivate(id);
        return "redirect:/admin/hotlines";
    }

    @GetMapping("/admin/medicines/add")
    public String addMedicineForm(Model model) {
        Medicine medicine = new Medicine();
        medicine.setCategory(new Category());
        model.addAttribute("medicine", medicine);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/medicine-form";
    }

    @GetMapping("/admin/medicines/edit/{id}")
    public String editMedicineForm(@PathVariable Long id, Model model) {
        Medicine medicine = service.getMedicine(id);
        if (medicine != null && medicine.getCategory() == null) {
            medicine.setCategory(new Category());
        }
        model.addAttribute("medicine", medicine);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/medicine-form";
    }

    @PostMapping("/admin/medicines/save")
    public String saveMedicine(@ModelAttribute Medicine medicine) {
        service.saveMedicine(medicine);
        return "redirect:/admin/medicines";
    }

    @PostMapping("/admin/medicines/toggle/{id}")
    public String toggleMedicine(@PathVariable Long id) {
        service.toggleActive(id);
        return "redirect:/admin/medicines";
    }

    @PostMapping("/admin/medicines/delete/{id}")
    public String deleteMedicine(@PathVariable Long id) {
        service.deleteMedicine(id);
        return "redirect:/admin/medicines";
    }

    @GetMapping("/admin/users")
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/admin-users";
    }

    @PostMapping("/admin/users/block/{id}")
    public String blockUser(@PathVariable Long id) {
        userService.blockUser(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/unblock/{id}")
    public String unblockUser(@PathVariable Long id) {
        userService.unblockUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        List<Medicine> allMedicines = service.getAllMedicines();
        List<Report> allReports = reportService.getAllReports();

        long pendingCount = allReports.stream().filter(r -> "PENDING".equals(r.getStatus())).count();
        long verifiedCount = allReports.stream().filter(r -> "VERIFIED".equals(r.getStatus())).count();
        long rejectedCount = allReports.stream().filter(r -> "REJECTED".equals(r.getStatus())).count();

        model.addAttribute("totalMedicines", allMedicines.size());
        model.addAttribute("activeMedicines", service.countActiveMedicines());
        model.addAttribute("pendingReports", pendingCount);
        model.addAttribute("verifiedFake", verifiedCount);
        model.addAttribute("rejectedReports", rejectedCount);
        model.addAttribute("blockedUsers", userService.countBlockedUsers());

        return "admin/admin-dashboard";
    }
}
