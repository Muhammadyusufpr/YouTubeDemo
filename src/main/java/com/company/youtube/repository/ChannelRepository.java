package com.company.youtube.repository;

import com.company.youtube.entity.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<ChannelEntity, String> {
    Optional<ChannelEntity> findByProfileId(Integer pId);


}
