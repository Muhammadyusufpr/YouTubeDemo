package com.company.youtube.repository;

import com.company.youtube.entity.VideoTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoTagRepository extends JpaRepository<VideoTagEntity, String> {
        void deleteByTagIdAndVideoId(String tId, String vId);

}
