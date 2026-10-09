package com.newsletter.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsletter.dto.request.WorkLocationCreateRequestDTO;
import com.newsletter.dto.response.WorkLocationDTO;
import com.newsletter.service.WorkLocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/work-locations")
public class WorkLocationController {

	private final WorkLocationService workLocationService;

    public WorkLocationController(WorkLocationService workLocationService) {
        this.workLocationService = workLocationService;
    }

    @GetMapping
    public ResponseEntity<List<WorkLocationDTO>> getAll() {
        return ResponseEntity.ok(workLocationService.getAllWorkLocations());
    }

    @PostMapping
    public ResponseEntity<WorkLocationDTO> create(@Valid @RequestBody WorkLocationCreateRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workLocationService.createWorkLocation(request));
    }
}