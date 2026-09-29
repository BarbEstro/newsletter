package com.newsletter.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsletter.entity.TeamEntity;
import com.newsletter.service.TeamService;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

	private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // GET /api/teams -> Lista dei team
    @GetMapping
    public ResponseEntity<List<TeamEntity>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    // POST /api/teams -> Creazione di un nuovo team
    @PostMapping
    public ResponseEntity<TeamEntity> createTeam(@RequestBody TeamEntity team) {
        TeamEntity savedTeam = teamService.insertTeam(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTeam);
    }
}