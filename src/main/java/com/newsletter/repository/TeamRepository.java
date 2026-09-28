package com.newsletter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newsletter.entity.TeamEntity;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
	
	boolean existsByName(String name);

}
