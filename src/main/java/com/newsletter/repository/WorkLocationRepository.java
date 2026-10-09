package com.newsletter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newsletter.entity.WorkLocationEntity;

public interface WorkLocationRepository extends JpaRepository<WorkLocationEntity, Long> {

}
