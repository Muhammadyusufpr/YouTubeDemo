package com.company.youtube.service;

import com.company.youtube.dto.TagDTO;
import com.company.youtube.entity.TagEntity;
import com.company.youtube.exception.ItemNotFoundException;
import com.company.youtube.exception.TagAlreadyExistsException;
import com.company.youtube.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public TagDTO create(TagDTO dto) {
        checkTagByName(dto.getName());

        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        tagRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public TagEntity checkTagByName(String name) {
        Optional<TagEntity> optionalTag = tagRepository.findByName(name);
        if (!optionalTag.isPresent()) {
            log.warn("tag alredy exists");
            throw new TagAlreadyExistsException("Tag already exists");
        }
        return optionalTag.get();
    }



    public TagDTO update(TagDTO dto) {
        checkTagByName(dto.getName());

        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        tagRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public Boolean delete(String id) {
        TagEntity entity = tagRepository.findById(id).orElseThrow(() ->
                new ItemNotFoundException("Not Found!"));
        log.info("Not Found");

        if (entity != null) {
            tagRepository.delete(entity);
            return true;
        }
        return false;
    }

    public List<TagDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        List<TagDTO> dtoList = new LinkedList<>();
        tagRepository.findAll(pageable).stream().forEach(tagEntity -> {
            dtoList.add(toDTO(tagEntity));
        });

        return dtoList;
    }

    private TagDTO toDTO(TagEntity tagEntity) {
        TagDTO dto = new TagDTO();
        dto.setName(tagEntity.getName());
        return dto;
    }

    public TagEntity get(String tagId) {
        return tagRepository.findById(tagId).orElseThrow(() -> {
            log.warn("Item not found");
            throw new ItemNotFoundException("Item not found");
        });
    }
}
