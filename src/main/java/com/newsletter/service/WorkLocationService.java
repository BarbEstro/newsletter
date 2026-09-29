package com.newsletter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.newsletter.entity.WorkLocationEntity;
import com.newsletter.repository.WorkLocationRepository;

@Service
public class WorkLocationService {
	
	private final WorkLocationRepository workLocationRepository;
	
	public WorkLocationService(WorkLocationRepository workLocationRepository) {
        this.workLocationRepository = workLocationRepository;
    }
	
	public WorkLocationEntity createWorkLocation(WorkLocationEntity workLocation) {
        return workLocationRepository.save(workLocation);
    }
	
	public List<WorkLocationEntity> getAllWorkLocations() {
        return workLocationRepository.findAll();
    }

}
