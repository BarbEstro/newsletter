package com.newsletter.dto.response;

import java.util.List;

public record EmployeeResponseDTO(
		
		Long id,
	    String fullName,
	    String email,
	    String workLocationName, // Nome semplice della sede
	    List<String> teamNames   // Lista di nomi dei team
	    ) {}
