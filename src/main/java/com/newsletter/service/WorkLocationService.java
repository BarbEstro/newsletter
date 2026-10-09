package com.newsletter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.newsletter.dto.request.WorkLocationCreateRequestDTO;
import com.newsletter.dto.response.WorkLocationDTO;
import com.newsletter.entity.WorkLocationEntity;
import com.newsletter.repository.WorkLocationRepository;

@Service
public class WorkLocationService {
	
	private final WorkLocationRepository workLocationRepository;
	
	public WorkLocationService(WorkLocationRepository workLocationRepository) {
        this.workLocationRepository = workLocationRepository;
    }
	
	public WorkLocationDTO createWorkLocation(WorkLocationCreateRequestDTO request) {
        WorkLocationEntity workLocation = new WorkLocationEntity();
        workLocation.setCity(request.city());
        workLocation.setAddress(request.address());
        return new WorkLocationDTO(workLocationRepository.save(workLocation));
    }
	
    
	public List<WorkLocationDTO> getAllWorkLocations() {
        return workLocationRepository.findAll().stream()
                .map(WorkLocationDTO::new)
                .toList();
    }

}
