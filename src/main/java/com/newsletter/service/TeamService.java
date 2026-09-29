package com.newsletter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.newsletter.entity.TeamEntity;
import com.newsletter.repository.TeamRepository;

@Service
public class TeamService {
	
	private final TeamRepository teamRepository;
	
	public TeamService(TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}
	
	public List<TeamEntity> getAllTeams() {
        return teamRepository.findAll();
    }
	
	public TeamEntity insertTeam(TeamEntity team) {
		if(teamRepository.existsByName(team.getName())) {
			throw new IllegalArgumentException("Nome del Team già esistente");
		}
		
		return teamRepository.save(team);
	}

}
