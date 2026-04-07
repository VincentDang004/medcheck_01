package com.medcheck.controller;

import com.medcheck.model.Category;
import com.medcheck.service.CategoryService;
import com.medcheck.service.MedicineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final MedicineService medicineService;

    public CategoryController(CategoryService categoryService, MedicineService medicineService) {
        this.categoryService = categoryService;
        this.medicineService = medicineService;
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("selectedCategoryId", null);
        return "user/categories";
    }

    @GetMapping("/categories/{id}")
    public String categoryMedicines(@PathVariable Integer id, Model model) {
        Category category = categoryService.getCategory(id);
        if (category == null) {
            return "redirect:/categories?notFound=true";
        }

        model.addAttribute("category", category);
        model.addAttribute("medicines", medicineService.getPublicMedicinesByCategory(id));
        model.addAttribute("selectedCategoryId", id);
        return "user/category";
    }
}
