package com.company.youtube.service;

import com.company.youtube.dto.PlayListVideoDTO;
import com.company.youtube.entity.PlayListEntity;
import com.company.youtube.entity.PlayListVideoEntity;
import com.company.youtube.entity.VideoEntity;
import com.company.youtube.exception.AppForbiddenException;
import com.company.youtube.exception.ItemNotFoundException;
import com.company.youtube.repository.PlayListVideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class PlayListVideoService {
    @Autowired
    private PlayListVideoRepository playListVideoRepository;
    @Autowired
    private PlayListService playListService;
    @Autowired
    private VideoService videoService;

    public PlayListVideoDTO create(PlayListVideoDTO dto) {
        PlayListVideoEntity entity = new PlayListVideoEntity();
        playListService.get(dto.getPlayListId());
        videoService.get(dto.getVideoId());
        entity.setPlayListId(dto.getPlayListId());
        entity.setVideoId(dto.getVideoId());
        entity.setOrderNum(dto.getOrderNum());
        playListVideoRepository.save(entity);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());
        return dto;
    }

    public  List<VideoEntity> getVideoByPlayListId(String id) {
        List<VideoEntity> videoList = new LinkedList<>();
        for (PlayListVideoEntity entity : playListVideoRepository.findByPlayListId(id)) {
            videoList.add(entity.getVideo());
        }
        return videoList;
    }

    public String update(Integer pId, PlayListVideoDTO dto) {
        PlayListEntity playlist = playListService.getById(dto.getPlayListId());
        if (!playlist.getChannel().getProfileId().equals(pId)){
            throw new AppForbiddenException("Not Access");
        }
        Optional<PlayListVideoEntity> optional = playListVideoRepository.findByPlayListIdAndVideoId(dto.getPlayListId(), dto.getVideoId());
        if (optional.isEmpty()){
            throw new ItemNotFoundException("Playlist Video Not Found");
        }
        PlayListVideoEntity entity = optional.get();
        entity.setOrderNum(dto.getOrderNum());
        entity.setUpdatedDate(LocalDateTime.now());
        playListVideoRepository.save(entity);
        return "Success";
    }

    public String  delete(Integer pId, String playlistId, String videoId) {
        Optional<PlayListVideoEntity> optional = playListVideoRepository.findByPlayListIdAndVideoId(playlistId, videoId);
        if (optional.isEmpty()){
            throw new ItemNotFoundException("Playlist Video Not Found");
        }
        PlayListVideoEntity entity = optional.get();
        PlayListEntity playlist = playListService.getById(entity.getPlayListId());
        if (!playlist.getChannel().getProfileId().equals(pId)){
            throw new AppForbiddenException("Not Access");
        }
        playListVideoRepository.delete(optional.get());
        return "Success";
    }

    public PlayListVideoDTO findByVideoId(String videoId){
        Optional<PlayListVideoEntity> entity = playListVideoRepository.findByVideoId(videoId);
        if (entity.isEmpty()){
            return null;
        }
        return toDTO(entity.get());
    }

    private PlayListVideoDTO toDTO(PlayListVideoEntity entity){
        PlayListVideoDTO dto = new PlayListVideoDTO();
        dto.setId(entity.getId());
        dto.setPlayListId(entity.getPlayListId());
        dto.setOrderNum(entity.getOrderNum());
        dto.setVideoId(entity.getVideoId());
        return dto;
    }

    public List<PlayListVideoDTO> getByPlaylistId(String playlistId) {
        List<PlayListVideoEntity> playlistVideoEntities = playListVideoRepository.findAllByPlayListId(playlistId);
        if (playlistVideoEntities.isEmpty()){
            return new LinkedList<>();
        }
        List<PlayListVideoDTO> playlistVideoDTOS = new ArrayList<>();

        playlistVideoEntities.forEach(entity -> {
            playlistVideoDTOS.add(playlistVideoInfo(entity));
        });
        return playlistVideoDTOS;
    }

    public PlayListVideoDTO playlistVideoInfo(PlayListVideoEntity entity){
        PlayListVideoDTO dto = new PlayListVideoDTO();
        dto.setId(entity.getId());
        dto.setPlayListId(entity.getPlayListId());
        dto.setVideoDTO(videoService.videoDTOForPlaylistVideo(entity.getVideoId()));
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setOrderNum(entity.getOrderNum());
        return dto;
    }


}
