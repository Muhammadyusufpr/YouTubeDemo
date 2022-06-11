package com.company.youtube.service;

import com.company.youtube.dto.PlayListDTO;
import com.company.youtube.dto.PlayListInfoDTO.AttachDTO;
import com.company.youtube.dto.PlayListInfoDTO.ChannelDTO;
import com.company.youtube.dto.PlayListInfoDTO.PlayListInfoDTO;
import com.company.youtube.dto.PlayListInfoDTO.ProfileDTO;
import com.company.youtube.dto.PlayListShortInfoDTO.PlayListShortInfoDTO;
import com.company.youtube.dto.PlayListShortInfoDTO.VideoDTO;
import com.company.youtube.enams.PlayListStatus;
import com.company.youtube.entity.ChannelEntity;
import com.company.youtube.entity.PlayListEntity;
import com.company.youtube.entity.VideoEntity;
import com.company.youtube.exception.AppForbiddenException;
import com.company.youtube.exception.ItemNotFoundException;
import com.company.youtube.repository.PlayListRepository;
import com.company.youtube.repository.PlayListVideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class PlayListService {
    @Autowired
    private PlayListRepository playListRepository;

    @Autowired
    @Lazy
    private PlayListVideoService playListVideoService;

    @Autowired
    private PlayListVideoRepository playListVideoRepository;

    @Autowired
    private ChannelService channelService;

    public PlayListDTO create(String channelId, PlayListDTO dto, Integer profileId) {
        ChannelEntity channelEntity = channelService.getById(channelId);
        if (!channelEntity.getProfileId().equals(profileId)) {
            log.warn("Not access {}", profileId);
            throw new AppForbiddenException("Not Access");
        }


        PlayListEntity entity = new PlayListEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setStatus(PlayListStatus.PUBLIC);
        entity.setOrderNum(dto.getOrderNum());
        entity.setChannelId(channelId);
        playListRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public PlayListDTO update(String id, PlayListDTO dto) {
        PlayListEntity entity = get(id);

        entity.setName(dto.getName());
        entity.setOrderNum(dto.getOrderNum());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        playListRepository.save(entity);
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }

    public PlayListEntity getById(String id) {
        return playListRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Playlist Not Found"));
    }


    public PlayListEntity get(String id) {
        return playListRepository.findById(id).orElseThrow(() -> {
            log.warn("Item not found");
            throw new ItemNotFoundException("Item not found");
        });
    }

    public Boolean delete(String id) {
        PlayListEntity entity = get(id);
        if (entity != null) {
            playListRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean changeStatus(String id) {
        PlayListEntity entity = get(id);
        if (entity.getStatus().equals(PlayListStatus.PRIVATE)) {
            entity.setStatus(PlayListStatus.PUBLIC);
        } else {
            entity.setStatus(PlayListStatus.PRIVATE);
        }
        return true;
    }

    public List<PlayListInfoDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "cratedDate");
        List<PlayListInfoDTO> dtoList = new LinkedList<>();
        playListRepository.findAll(pageable).forEach(playListEntity -> {
            dtoList.add(toPlayListInfoDTO(playListEntity));
        });
        return dtoList;
    }

    public PlayListInfoDTO toPlayListInfoDTO(PlayListEntity entity) {
        PlayListInfoDTO dto = new PlayListInfoDTO();
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setId(entity.getId());
        dto.setOrderNum(entity.getOrderNum());
        dto.setChannel(new ChannelDTO(entity.getChannelId(), entity.getName(),
                new AttachDTO(entity.getChannel().getAttachId(),
                        "http://localhost:8080/attach/open_general/" + entity.getChannel().getAttachId())));

        dto.setProfile(new ProfileDTO(entity.getProfile().getId(), entity.getProfile().getName(),
                entity.getProfile().getSurname(), new AttachDTO(entity.getChannel().getAttachId(),
                "http://localhost:8080/attach/open_general/" + entity.getChannel().getAttachId())));

        return dto;
    }

    public List<PlayListInfoDTO> listByUserId(Integer userId, int page, int size, Integer ordeerNum) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, String.valueOf(ordeerNum));
        List<PlayListInfoDTO> dtoList = new LinkedList<>();
        playListRepository.findByProfileId(userId, pageable).forEach(playListEntity -> {
            dtoList.add(toPlayListInfoDTO(playListEntity));
        });

        return dtoList;
    }

    public List<PlayListShortInfoDTO> getUserPlayList(Integer pId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "orderNum");
        List<PlayListShortInfoDTO> dtoList = new LinkedList<>();
        playListRepository.findByProfileId(pId, pageable).forEach(entity -> {
            dtoList.add(toPlayListShortInfoDTO(entity));
        });
        return dtoList;
    }

    private PlayListShortInfoDTO toPlayListShortInfoDTO(PlayListEntity entity) {
        PlayListShortInfoDTO dto = new PlayListShortInfoDTO();
       dto.setViewCount(playListVideoRepository.getTotalViewCount(entity.getViewCount()));
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setChannel(new com.company.youtube.dto.PlayListShortInfoDTO.ChannelDTO(
                entity.getChannel().getId(), entity.getChannel().getName()
        ));

        dto.setVideoList(getVideoDTOlist(playListVideoService.
                getVideoByPlayListId(entity.getId())));
        return dto;
    }

    private List<VideoDTO> getVideoDTOlist(List<VideoEntity> list) {
        List<VideoDTO> dtoList = new LinkedList<>();
        for (VideoEntity video : list) {
            dtoList.add(new VideoDTO(video.getId(), video.getTitle(), 100.0)); // TODO: 20.05.2022 I have to add duration !
        }
        return dtoList;
    }

    public List<PlayListShortInfoDTO> getChannelPlayListByChannelId(String channelId) {
//        ChannelEntity channelEntity = channelService.getById(channelId);

        List<PlayListShortInfoDTO> dtoList = new LinkedList<>();
        playListRepository.findByChannelId(channelId).forEach(entity -> {
            dtoList.add(toPlayListShortInfoDTO(entity));
        });
        return dtoList;
    }
}
