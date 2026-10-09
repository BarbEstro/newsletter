package com.newsletter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.newsletter.dto.request.TeamCreateRequestDTO;
import com.newsletter.dto.response.TeamResponseDTO;
import com.newsletter.entity.TeamEntity;
import com.newsletter.repository.TeamRepository;

@Service
public class TeamService {
	
	private final TeamRepository teamRepository;
	
	public TeamService(TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	public TeamResponseDTO getTeamById(Long id) {
		TeamEntity team = teamRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Team non trovato"));
		return new TeamResponseDTO(team);
	}

	public List<TeamResponseDTO> getAllTeams() {
		return teamRepository.findAll().stream()
				.map(TeamResponseDTO::new)
				.toList();
	}
	
	public TeamResponseDTO insertTeam(TeamCreateRequestDTO request) {
		if(teamRepository.existsByName(request.name())) {
			throw new IllegalArgumentException("Nome del Team già esistente");
		}
		
		TeamEntity team = new TeamEntity(request.name());
		return new TeamResponseDTO(teamRepository.save(team));
	}

}
