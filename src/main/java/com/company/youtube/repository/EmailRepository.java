package com.company.youtube.repository;

import com.company.youtube.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailEntity, String> {

}
