package com.company.youtube.service;

import com.company.youtube.dto.FullVideoInfoDTO.AttachDTO;
import com.company.youtube.dto.FullVideoInfoDTO.CategoryDTO;
import com.company.youtube.dto.FullVideoInfoDTO.FullVideoInfoDTO;
import com.company.youtube.dto.TagDTO;
import com.company.youtube.dto.VideoDTO;
import com.company.youtube.dto.VideoDTOForPlaylistVideoDTO;
import com.company.youtube.dto.VideoShortInfoDTO.ChannelDTO;
import com.company.youtube.dto.VideoShortInfoDTO.PreviewAttachDTO;
import com.company.youtube.dto.VideoShortInfoDTO.VideoShortInfoDTO;
import com.company.youtube.enams.LikeType;
import com.company.youtube.enams.ProfileRole;
import com.company.youtube.enams.VideoStatus;
import com.company.youtube.entity.*;
import com.company.youtube.exception.AppBadRequestException;
import com.company.youtube.exception.ItemNotFoundException;
import com.company.youtube.repository.VideoRepository;
import com.company.youtube.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private TagService tagService;

    @Autowired
    private AttachService attachService;


    public VideoDTO create(VideoDTO dto) {
        VideoEntity entity = new VideoEntity();
        entity.setPreviewAttachId(dto.getPreviewAttachId());
        entity.setTitle(dto.getTitle());
        entity.setCategoryId(dto.getCategoryId());
        entity.setAttachId(dto.getAttachId());
        entity.setStatus(dto.getStatus());
        entity.setType(dto.getType());
        entity.setDescription(dto.getDescription());
        entity.setChannelId(dto.getChannelId());
        videoRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    public VideoEntity get(String id) {
        return videoRepository.findById(id).orElseThrow(() -> {
            log.warn("Item not found");
            throw new ItemNotFoundException("Item not found");
        });
    }


    public VideoDTO getById(String id) {
        VideoEntity entity = videoRepository.findById(id).orElseThrow(() ->
                new ItemNotFoundException("Not found"));
        return toDTO(entity);
    }

    private VideoDTO toDTO(VideoEntity entity) {
        VideoDTO dto = new VideoDTO();
        dto.setId(entity.getId());
        dto.setAttachId(entity.getAttachId());
        dto.setPreviewAttachId(entity.getPreviewAttachId());
        dto.setTitle(entity.getTitle());
        dto.setChannelId(entity.getChannelId());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setType(entity.getType());
        dto.setLikeCount(getLikeCount(entity, LikeType.LIKE));
        dto.setDisLikeCount(getLikeCount(entity, LikeType.DISLIKE));
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCategoryId(entity.getCategoryId());
        return dto;
    }

    private Integer getLikeCount(VideoEntity entity, LikeType type) {
        int count = 0;
        for (VideoLikeEntity videoLikeEntity : entity.getLikeEntity()) {
            if (videoLikeEntity.getType().equals(type)) count++;
        }
        return count;
    }

    public VideoDTO update(String id, VideoDTO dto, Integer pId) {
        ChannelEntity entity = channelService.getByPId(pId);
        if (entity == null) throw new AppBadRequestException("you are not owner");

        VideoEntity videoEntity = videoRepository.getById(id);
        if (!videoEntity.getChannelId().equals(entity.getId())) {
            throw new AppBadRequestException("bu video channelda yo'q");
        }

        videoEntity.setTitle(dto.getTitle());
        videoEntity.setDescription(dto.getDescription());
        videoEntity.setType(dto.getType());
        videoEntity.setPreviewAttachId(dto.getPreviewAttachId());
        videoRepository.save(videoEntity);
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }

    public Boolean delete(String vId, Integer pId) {
        VideoEntity videoEntity = videoRepository.getById(vId);
        videoRepository.deleteByIdAndProfileId(videoEntity.getId(), pId);

        return true;
    }


    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< videoShortInfo >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public List<VideoShortInfoDTO> getCategoryPagination(String categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdDate");

        List<VideoShortInfoDTO> dtoList = new LinkedList<>();
        videoRepository.findByCategoryId(categoryId, pageable).forEach(entity -> {
            dtoList.add(toVideoShortInfoDTO(entity));
        });
        if (dtoList.isEmpty()) {
            log.warn("Item not found");
            throw new ItemNotFoundException("Item not found");
        }
        return dtoList;
    }

    public VideoShortInfoDTO toVideoShortInfoDTO(VideoEntity entity) {
        VideoShortInfoDTO dto = new VideoShortInfoDTO();
        dto.setTitle(entity.getTitle());
        dto.setPreviewAttach(new PreviewAttachDTO(entity.getPreviewAttachId(),
                "http://localhost:8080/attach/open_general/" + entity.getPreviewAttachId()));
        dto.setViewCount(entity.getViewCount());
        dto.setId(entity.getId().toString());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setChannelDTO(new ChannelDTO(entity.getChannelId(),
                "http://localhost:8080/attach/open_general/" + entity.getChannel().getAttachId(), entity.getChannel().getName()));
        return dto;
    }

    public List<VideoShortInfoDTO> getSearchVideoByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdDate");

        List<VideoShortInfoDTO> dtoList = new LinkedList<>();
        videoRepository.findByTitle(title, pageable).forEach(entity -> {
            dtoList.add(toVideoShortInfoDTO(entity));
        });
        if (dtoList.isEmpty()) {
            log.warn("Item not found");
            throw new ItemNotFoundException("Item not found");
        }
        return dtoList;
    }


//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< FullVideoInfoDTO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public FullVideoInfoDTO getVideoById(String vId, HttpServletRequest request) {
        Optional<VideoEntity> optional = videoRepository.findById(vId);
        if (optional.isEmpty()) throw new ItemNotFoundException("Item not found !");
        VideoEntity entity = optional.get();

        if (entity.getStatus().equals(VideoStatus.PRIVATE)) {
            JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN, ProfileRole.OWNER);
        }

        FullVideoInfoDTO dto = new FullVideoInfoDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setCategory(new CategoryDTO(entity.getCategoryId(), entity.getCategory().getName()));
        dto.setViewCount(entity.getViewCount());
        dto.setAttach(new AttachDTO(entity.getAttachId(),
                getUrl(entity.getAttachId()), 100.0));
        dto.setChannel(new ChannelDTO(entity.getChannelId(), getUrl(entity.getChannel().getAttachId()),
                entity.getChannel().getName()));


        dto.setPreviewAttach(new PreviewAttachDTO(
                entity.getPreviewAttachId(),
                getUrl(entity.getPreviewAttachId())));
        List<TagDTO> tagList = new ArrayList<>();
        for (VideoTagEntity videoTagEntity : entity.getVideoTagEntities()) {
            tagList.add(toTagDTO(tagService.get(videoTagEntity.getTagId())));
        }
        dto.setTagList(tagList);
        return dto;
    }

    private String getUrl(String id) {
        return "http://localhost:8080/attach/open_general/" + id;
    }

    private TagDTO toTagDTO(TagEntity entity) {
        TagDTO dto = new TagDTO();
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }



    public VideoDTOForPlaylistVideoDTO videoDTOForPlaylistVideo(String videoId) {
        VideoEntity entity = getByIdForPlayList(videoId);
        VideoDTOForPlaylistVideoDTO dto = new VideoDTOForPlaylistVideoDTO();
        dto.setId(entity.getId());
        dto.setPreviewAttach(attachService.toOpenURLDTO(entity.getPreviewAttachId()));
        dto.setTitle(entity.getTitle());
        dto.setChannel(channelService.toSimpleDTO(entity.getChannelId()));
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public VideoEntity getByIdForPlayList(String id){
        return videoRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Video Not Found"));
    }


}
