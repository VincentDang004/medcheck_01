package com.medcheck.service;

import com.medcheck.model.Category;
import com.medcheck.repository.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getAllCategories() {
        return repository.findByStatusTrueOrderByCategoryNameAsc();
    }

    public List<Category> getAllCategoriesAdmin() {
        return repository.findAllByOrderByCategoryNameAsc();
    }

    public Category getCategory(Integer id) {
        return repository.findByIdAndStatusTrue(id).orElse(null);
    }

    public Category getCategoryAdmin(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void saveCategory(Category category) {
        repository.save(category);
    }

    public void toggleStatus(Integer id) {
        Category category = getCategoryAdmin(id);
        if (category != null) {
            category.setStatus(!category.isStatus());
            repository.save(category);
        }
    }

    public void deactivate(Integer id) {
        Category category = getCategoryAdmin(id);
        if (category != null) {
            category.setStatus(Boolean.FALSE);
            repository.save(category);
        }
    }
}
