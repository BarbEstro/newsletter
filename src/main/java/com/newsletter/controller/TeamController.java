package com.newsletter.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsletter.dto.request.TeamCreateRequestDTO;
import com.newsletter.dto.response.TeamResponseDTO;
import com.newsletter.service.TeamService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // GET /api/teams -> Lista di tutti i team
    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    // GET /api/teams/{id} -> Singolo team
    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> getTeamById(@PathVariable Long id){
        TeamResponseDTO team = teamService.getTeamById(id);
        return ResponseEntity.ok(team);
    }

    // POST /api/teams -> Creazione di un nuovo team
    @PostMapping
    public ResponseEntity<TeamResponseDTO> createTeam(@Valid @RequestBody TeamCreateRequestDTO request) {
        TeamResponseDTO savedTeam = teamService.insertTeam(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTeam);
    }
}
