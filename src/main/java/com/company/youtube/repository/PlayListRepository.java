package com.company.youtube.repository;

import com.company.youtube.entity.PlayListEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlayListRepository extends JpaRepository<PlayListEntity, String> {
//    List<PlayListEntity> getAll(Pageable pageable);

    Page<PlayListEntity> findByProfileId(Integer id, Pageable pageable);

    Iterable<PlayListEntity> findByChannelId(String id);
}
