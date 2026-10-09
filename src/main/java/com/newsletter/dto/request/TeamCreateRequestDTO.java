package com.newsletter.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TeamCreateRequestDTO(
        @NotBlank(message = "Il nome del team è obbligatorio")
        String name
) {}
