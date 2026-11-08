package com.medcheck.repository;

import com.medcheck.model.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByStatusTrueOrderByCategoryNameAsc();

    Optional<Category> findByIdAndStatusTrue(Integer id);

    List<Category> findAllByOrderByCategoryNameAsc();
}
