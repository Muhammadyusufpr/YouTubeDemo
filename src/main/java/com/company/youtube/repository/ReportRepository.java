package com.company.youtube.repository;

import com.company.youtube.entity.ReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {
    Page<ReportEntity> findByProfileId(Integer profileId, Pageable pageable);
}