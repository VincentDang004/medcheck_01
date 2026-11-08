package com.medcheck.repository;

import com.medcheck.model.Medicine;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findByActiveTrue();

    List<Medicine> findByActiveTrueAndCategory_Id(Integer categoryId);

    List<Medicine> findByActiveTrueAndNameContainingIgnoreCaseOrActiveTrueAndCodeContainingIgnoreCase(
            String name, String code);

    Optional<Medicine> findByIdAndActiveTrue(Long id);
}
