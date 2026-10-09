package com.newsletter.dto.response;

import com.newsletter.entity.TeamEntity;

public record TeamResponseDTO(
    Long id,
    String name
) {
    public TeamResponseDTO(TeamEntity team) {
        this(team.getTeamId(), team.getName());
    }
}
