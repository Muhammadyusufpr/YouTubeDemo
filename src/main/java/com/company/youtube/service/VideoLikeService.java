package com.company.youtube.service;

import com.company.youtube.dto.VideoLikeDTO;
import com.company.youtube.enams.LikeType;
import com.company.youtube.entity.VideoLikeEntity;
import com.company.youtube.repository.VideoLikeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VideoLikeService {
    @Autowired
    private VideoLikeRepository videoLikeRepository;


    public VideoLikeDTO create(String videoId, LikeType type, Integer pId) {
        if (videoLikeRepository.findByProfileId(pId) != null) {
            videoLikeRepository.updateLikeType(type, pId);
        }

        VideoLikeEntity entity = new VideoLikeEntity();
        entity.setType(type);
        entity.setVideoId(videoId);
        entity.setProfileId(pId);
        videoLikeRepository.save(entity);

        VideoLikeDTO dto = new VideoLikeDTO();
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setProfileId(entity.getProfileId());
        dto.setVideoId(entity.getVideoId());
        dto.setId(entity.getId());
        dto.setLikeType(entity.getType());
        return dto;
    }

    public Boolean delete(String videoId, LikeType type, Integer pId) {
        videoLikeRepository.deleteByProfileIdAndVideoIdAndType(pId, videoId, type);
        return true;
    }
}
