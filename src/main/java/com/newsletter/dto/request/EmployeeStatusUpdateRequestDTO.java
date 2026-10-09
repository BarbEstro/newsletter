package com.newsletter.dto.request;

import com.newsletter.entity.EmployeeStatus;

import jakarta.validation.constraints.NotNull;

public record EmployeeStatusUpdateRequestDTO(
		@NotNull(message = "Invalid value")
		EmployeeStatus status
		) {}
