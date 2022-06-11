package com.company.youtube.service;

import com.company.youtube.dto.CommentDTO;
import com.company.youtube.entity.CommentEntity;
import com.company.youtube.entity.VideoEntity;
import com.company.youtube.exception.ItemNotFoundException;
import com.company.youtube.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public CommentDTO create(CommentDTO dto) {
        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setProfileId(dto.getProfileId());
        entity.setLikeType(dto.getLikeType());
        entity.setVideoId(dto.getVideoId());
        commentRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CommentDTO update(String cId, CommentDTO dto) {
        CommentEntity entity = get(cId);

        entity.setReplyId(dto.getReplyId());
        entity.setContent(dto.getContent());
        entity.setLikeType(dto.getLikeType());
        commentRepository.save(entity);
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }


    public CommentEntity get(String id) {
        return commentRepository.findById(id).orElseThrow(()->{
            log.warn("Item Not Found");
            throw new ItemNotFoundException("Item Not Found");
        });
    }

    public Boolean delete(String cId) {
        CommentEntity entity = get(cId);
        commentRepository.deleteById(cId);
        return true;
    }

    public List<CommentDTO> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        List<CommentDTO> dtoList = new LinkedList<>();
        commentRepository.findAll(pageable).stream().forEach(commentEntity -> {
            dtoList.add(toDTO(commentEntity));
        });
        return dtoList;
    }

    private CommentDTO toDTO(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setContent(entity.getContent());
        dto.setLikeType(entity.getLikeType());
        dto.setReplyId(entity.getReplyId());
        dto.setProfileId(entity.getProfileId());
        dto.setVideoId(entity.getVideoId());
        return dto;
    }


    public List<CommentDTO> listByProfileId(Integer pId) {
        List<CommentDTO> dtoList = new LinkedList<>();
        commentRepository.findByProfileId(pId).forEach(commentEntity -> {
            dtoList.add(toDTO(commentEntity));
        });
        if (dtoList.isEmpty()) {
            throw new ItemNotFoundException("Item not found");
        }
        return dtoList;
    }
}
