package com.medcheck.repository;

import com.medcheck.model.Faq;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Long> {
    List<Faq> findByStatusTrueOrderByDisplayOrderAscIdAsc();

    List<Faq> findAllByOrderByDisplayOrderAscIdAsc();
}

