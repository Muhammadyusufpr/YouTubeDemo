package com.company.youtube.repository;

import com.company.youtube.enams.NotificationType;
import com.company.youtube.enams.SubscriptionStatus;
import com.company.youtube.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer> {
    Optional<SubscriptionEntity> findByProfileIdAndId(Integer profileId, Integer id);

    @Transactional
    @Modifying
    @Query("update SubscriptionEntity as a set a.status=:status where a.id=:id")
    int updateStatus(@Param("status") SubscriptionStatus status, @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("update SubscriptionEntity as a set a.type=:type where a.id=:id")
    int updateType(@Param("type") NotificationType type, @Param("id") Integer id);

    List<SubscriptionEntity> findByProfileIdAndStatus(Integer profileId, SubscriptionStatus status);
}