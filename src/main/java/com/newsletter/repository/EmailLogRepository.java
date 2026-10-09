package com.newsletter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newsletter.entity.EmailLogEntity;

public interface EmailLogRepository extends JpaRepository<EmailLogEntity, Long> {

}
