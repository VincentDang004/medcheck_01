package com.medcheck.controller;

import com.medcheck.model.Category;
import com.medcheck.service.CategoryService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = Controller.class)
public class GlobalModelAttributes {

    private final CategoryService categoryService;

    public GlobalModelAttributes(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ModelAttribute("navCategories")
    public List<Category> navCategories() {
        return categoryService.getAllCategories();
    }
}
