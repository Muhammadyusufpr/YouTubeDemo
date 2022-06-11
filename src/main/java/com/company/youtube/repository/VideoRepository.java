package com.company.youtube.repository;

import com.company.youtube.entity.VideoEntity;
import com.company.youtube.entity.VideoTagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface VideoRepository extends JpaRepository<VideoEntity, String> {
    Page<VideoEntity> findByCategoryId(String id, Pageable pageable);

    Page<VideoEntity> findByTitle(String title, Pageable pageable);




//    Page<VideoEntity> findByTagId(String name, Pageable pageable);

    void deleteByIdAndProfileId(String id, Integer profileId);
}
