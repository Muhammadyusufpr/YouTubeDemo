package com.company.youtube.repository;

import com.company.youtube.entity.CommentEntity;
import com.company.youtube.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, String> {
    List<CommentEntity> findByProfileId(Integer id);

}
