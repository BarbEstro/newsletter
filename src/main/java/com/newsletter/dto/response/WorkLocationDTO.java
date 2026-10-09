package com.newsletter.dto.response;
import com.newsletter.entity.WorkLocationEntity;

public record WorkLocationDTO(
    Long id,
    String city,
    String address
) {
    public WorkLocationDTO(WorkLocationEntity workLocation) {
        this(workLocation.getWorkLocationId(), workLocation.getCity(), workLocation.getAddress());
    }
}
