package com.company.youtube.service;

import com.company.youtube.dto.ChannelDTO;
import com.company.youtube.entity.ChannelEntity;
import com.company.youtube.entity.VideoEntity;
import com.company.youtube.exception.AppBadRequestException;
import com.company.youtube.exception.ItemNotFoundException;
import com.company.youtube.repository.ChannelRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    AttachService attachService;

    public ChannelDTO create(ChannelDTO dto, Integer pId) {
        ChannelEntity entity = new ChannelEntity();
        entity.setName(dto.getName());
        entity.setProfileId(pId);
        entity.setKey(dto.getKey());
        entity.setDescription(dto.getDescription());
        channelRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ChannelDTO update(ChannelDTO dto, Integer pId) {
        ChannelEntity entity = new ChannelEntity();
        get(pId);
        entity.setProfileId(pId);
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setKey(dto.getKey());
        channelRepository.save(entity);
        dto.setId(entity.getId());
        dto.setUpdatedDate(dto.getUpdatedDate());
        return dto;
    }

    public Boolean updatePhoto(String aid, Integer pId) {
        ChannelEntity entity = get(pId);

        if (!entity.getProfileId().equals(pId)) {
            throw new AppBadRequestException("you are not owner");
        }

        if (entity.getAttachId() != null) {
            attachService.delete(entity.getAttachId());
        }

        entity.setAttachId(aid);

        return true;
    }

    private ChannelEntity get(Integer pId) {
        ChannelEntity entity = channelRepository.findByProfileId(pId).orElseThrow(() ->
                new ItemNotFoundException("pId not found"));
        log.warn("pId not found");
        return entity;
    }


    public Boolean updateBanner(String bid, Integer pId) {
        ChannelEntity entity = get(pId);
        if (!entity.getProfileId().equals(pId)) {
            throw new AppBadRequestException("you are not owner");
        }

        if (entity.getBannerId() == null) {
            entity.setBannerId(bid);
        } else attachService.delete(entity.getBannerId());
        return true;
    }

    public List<ChannelDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        List<ChannelDTO> dtoList = new LinkedList<>();
        channelRepository.findAll(pageable).stream().forEach(channelEntity -> {
            dtoList.add(toDTO(channelEntity));
        });
        return dtoList;
    }

    public ChannelDTO toDTO(ChannelEntity entity) {
        ChannelDTO dto = new ChannelDTO();
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setKey(entity.getKey());
        return dto;
    }

    public ChannelEntity getByPId(Integer pId) {
        ChannelEntity entity = channelRepository.findByProfileId(pId).orElseThrow(() ->
                new ItemNotFoundException("Channel not found"));
        log.warn("Channel not found");
        return entity;
    }

    public ChannelEntity getById(String pId) {
        ChannelEntity entity = channelRepository.findById(pId).orElseThrow(() ->
                new ItemNotFoundException("Channel not found"));
        log.warn("Channel not found");
        return entity;
    }

    public com.company.youtube.dto.VideoShortInfoDTO.ChannelDTO toSimpleDTO(String channelId){
        ChannelEntity entity = getForString(channelId);
        com.company.youtube.dto.VideoShortInfoDTO.ChannelDTO dto = new com.company.youtube.dto.VideoShortInfoDTO.ChannelDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        if (entity.getAttachId() != null){
            dto.setPhoto(String.valueOf(attachService.toOpenURLDTO(String.valueOf(entity.getAttach()))));
        }
        return dto;
    }

    private ChannelEntity getForString(String pId) {
        ChannelEntity entity = channelRepository.findById(pId).orElseThrow(() ->
                new ItemNotFoundException("pId not found"));
        log.warn("pId not found");
        return entity;
    }



}
