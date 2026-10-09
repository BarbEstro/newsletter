package com.newsletter.dto.request;

import jakarta.validation.constraints.NotBlank;

public record WorkLocationCreateRequestDTO(
        @NotBlank(message = "La città è obbligatoria")
        String city,

        @NotBlank(message = "L'indirizzo è obbligatorio")
        String address
) {}
