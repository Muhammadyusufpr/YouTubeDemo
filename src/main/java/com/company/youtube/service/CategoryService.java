package com.company.youtube.service;

import com.company.youtube.dto.CategoryDTO;
import com.company.youtube.entity.CategoryEntity;
import com.company.youtube.exception.ItemNotFoundException;
import com.company.youtube.repository.CategoryRepository;
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
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    public CategoryDTO create(CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        categoryRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CategoryDTO update(String id, CategoryDTO dto) {
        categoryRepository.findById(id);
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        categoryRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public Boolean delete(String id) {
        CategoryEntity entity = categoryRepository.findById(id).orElseThrow(() ->
                new ItemNotFoundException("Not Found!"));
        log.info("Not Found");

        if (entity != null) {
            categoryRepository.delete(entity);
            return true;
        }
        return false;
    }

    public List<CategoryDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        List<CategoryDTO> dtoList = new LinkedList<>();
        categoryRepository.findAll(pageable).stream().forEach(categoryEntity -> {
            dtoList.add(toDTO(categoryEntity));
        });
        if (dtoList.isEmpty()) {
            throw new ItemNotFoundException("Not found");
        }

        return dtoList;
    }

    private CategoryDTO toDTO(CategoryEntity categoryEntity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(categoryEntity.getId());
        dto.setName(categoryEntity.getName());
        return dto;
    }
}
