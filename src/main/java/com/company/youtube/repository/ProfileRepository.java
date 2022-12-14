package com.company.youtube.repository;

import com.company.youtube.enams.ProfileStatus;
import com.company.youtube.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findByEmailAndPassword(String email, String password);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible = :visible where id = :id")
    void updateVisible(@Param("visible") Boolean visible, @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set attach.id = :attachId where id = :id")
    int updateAttach(@Param("attachId") String attachId, @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status = :status where id = :id")
    int updateStatus(@Param("status") ProfileStatus status, @Param("id") Integer id);
}
