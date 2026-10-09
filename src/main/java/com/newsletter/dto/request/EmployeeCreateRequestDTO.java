package com.newsletter.dto.request;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmployeeCreateRequestDTO(
	    @NotBlank(message = "Il nome è obbligatorio")
	    String name,

	    @NotBlank(message = "Il cognome è obbligatorio")
	    String surname,

	    @NotBlank @Email(message = "Email non valida")
	    String email,

	    @NotNull(message = "La data di nascita è obbligatoria")
	    LocalDate dateOfBirth,
	    
	    @NotNull(message = "inserire la sede lavorativa")
	    Long workLocationId,
	    
	    Set<Long> teams
	) {}