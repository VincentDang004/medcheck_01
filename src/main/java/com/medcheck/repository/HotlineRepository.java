package com.medcheck.repository;

import com.medcheck.model.Hotline;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotlineRepository extends JpaRepository<Hotline, Long> {
    List<Hotline> findByStatusTrueOrderByDisplayOrderAscIdAsc();

    List<Hotline> findAllByOrderByDisplayOrderAscIdAsc();
}

