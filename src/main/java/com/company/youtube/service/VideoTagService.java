package com.company.youtube.service;

import com.company.youtube.dto.VideoTagDTO;
import com.company.youtube.entity.VideoEntity;
import com.company.youtube.entity.VideoTagEntity;
import com.company.youtube.exception.ItemNotFoundException;
import com.company.youtube.repository.VideoTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VideoTagService {
    @Autowired
    private VideoTagRepository videoTagRepository;

    @Autowired
    private VideoService videoService;

    public VideoTagDTO create(VideoTagDTO dto) {
        VideoTagEntity entity = new VideoTagEntity();
        entity.setTagId(dto.getTagId());
        entity.setVideoId(entity.getVideoId());
        videoTagRepository.save(entity);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());
        return dto;
    }


    private VideoTagEntity get(String id) {
        return videoTagRepository.findById(id).orElseThrow(() -> {
            log.info("Item not found");
            throw new ItemNotFoundException("Item not found!");
        });
    }

    public Boolean delete(String tId, String vId) {
        videoService.get(vId);
        get(tId);
        videoTagRepository.deleteByTagIdAndVideoId(tId, vId);
        return true;
    }
}
