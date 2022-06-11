package com.company.youtube.repository;

import com.company.youtube.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, String> {
    Optional<TagEntity> findByName(String name);
}
