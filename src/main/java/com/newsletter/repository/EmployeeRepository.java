package com.newsletter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newsletter.entity.EmployeeEntity;


public interface EmployeeRepository extends JpaRepository <EmployeeEntity, Long> {
	
	boolean existsByEmail(String email);

}
