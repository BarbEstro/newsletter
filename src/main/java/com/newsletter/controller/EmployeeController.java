package com.newsletter.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsletter.dto.request.EmployeeCreateRequestDTO;
import com.newsletter.dto.request.EmployeeStatusUpdateRequestDTO;
import com.newsletter.dto.response.EmployeeResponseDTO;
import com.newsletter.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // GET /api/employees/{id} -> Singolo dipendente
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<EmployeeResponseDTO>> getActiveEmployees() {
        return ResponseEntity.ok(employeeService.getActiveEmployees());
    }

    // POST /api/employees -> Creazione nuovo dipendente
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeCreateRequestDTO request) {
        EmployeeResponseDTO savedEmployee = employeeService.registerEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    // PATCH /api/employees/{id}/status -> Cambia lo stato (ACTIVE/INACTIVE)
    @PatchMapping("/{id}/status")
    public ResponseEntity<EmployeeResponseDTO> changeStatus(@PathVariable Long id,
            @Valid @RequestBody EmployeeStatusUpdateRequestDTO request) {
        return employeeService.changeStatus(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}