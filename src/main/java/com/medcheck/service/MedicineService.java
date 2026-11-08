package com.medcheck.service;

import com.medcheck.model.Medicine;
import com.medcheck.repository.MedicineRepository;
import com.medcheck.repository.ReportRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MedicineService {

    private final MedicineRepository repository;
    @SuppressWarnings("unused")
    private final ReportRepository reportRepository;

    public MedicineService(MedicineRepository repository, ReportRepository reportRepository) {
        this.repository = repository;
        this.reportRepository = reportRepository;
    }

    public List<Medicine> search(String keyword) {
        return repository.findByActiveTrueAndNameContainingIgnoreCaseOrActiveTrueAndCodeContainingIgnoreCase(
                keyword, keyword);
    }

    public List<Medicine> getAllMedicines() {
        return repository.findAll();
    }

    public List<Medicine> getPublicMedicines() {
        return repository.findByActiveTrue();
    }

    public List<Medicine> getPublicMedicinesByCategory(Integer categoryId) {
        return repository.findByActiveTrueAndCategory_Id(categoryId);
    }

    public Medicine getMedicine(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Medicine getPublicMedicine(Long id) {
        return repository.findByIdAndActiveTrue(id).orElse(null);
    }

    public void saveMedicine(Medicine medicine) {
        repository.save(medicine);
    }

    public void toggleActive(Long id) {
        Medicine medicine = getMedicine(id);
        if (medicine != null) {
            medicine.setActive(!medicine.isActive());
            repository.save(medicine);
        }
    }

    public long countActiveMedicines() {
        return repository.findByActiveTrue().size();
    }

    public void deleteMedicine(Long id) {
        repository.deleteById(id);
    }
}
