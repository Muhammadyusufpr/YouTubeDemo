package com.company.youtube.repository;

import com.company.youtube.enams.LikeType;
import com.company.youtube.entity.VideoLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface VideoLikeRepository extends JpaRepository<VideoLikeEntity, String> {

    @Transactional
    @Modifying
    @Query("update VideoLikeEntity set type = :type where profileId = :id")
    int updateLikeType(@Param("type") LikeType type, @Param("id") Integer id);


    VideoLikeEntity findByProfileId(Integer pId);

    void deleteByProfileIdAndVideoIdAndType(Integer pId, String videoId, LikeType type);

}
