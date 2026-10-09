package com.newsletter.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.newsletter.dto.request.EmployeeCreateRequestDTO;
import com.newsletter.dto.request.EmployeeStatusUpdateRequestDTO;
import com.newsletter.dto.response.EmployeeResponseDTO;
import com.newsletter.entity.EmployeeEntity;
import com.newsletter.entity.EmployeeStatus;
import com.newsletter.entity.TeamEntity;
import com.newsletter.entity.WorkLocationEntity;
import com.newsletter.repository.EmployeeRepository;
import com.newsletter.repository.TeamRepository;
import com.newsletter.repository.WorkLocationRepository;

@Service
public class EmployeeService {
	
	private final EmployeeRepository employeeRepository;
	private final WorkLocationRepository workLocationRepository;
	private final TeamRepository teamRepository;

	public EmployeeService(EmployeeRepository employeeRepository, WorkLocationRepository workLocationRepository,
			TeamRepository teamRepository) {
		this.employeeRepository = employeeRepository;
		this.workLocationRepository = workLocationRepository;
		this.teamRepository = teamRepository;
	}
	
	public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }
	
	public List<EmployeeResponseDTO> getActiveEmployees() {
        return employeeRepository.findAll().stream()
                .filter(emp -> EmployeeStatus.ACTIVE.equals(emp.getEmployeeStatus()))
                .map(this::toResponseDTO)
                .toList();
    }
	
	public Optional<EmployeeResponseDTO> getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(this::toResponseDTO);
    }
	
	public EmployeeResponseDTO registerEmployee(EmployeeCreateRequestDTO request) {
        if (employeeRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Impossibile registrare: l'email " + request.email() + " esiste già.");
        }

        WorkLocationEntity workLocation = workLocationRepository.findById(request.workLocationId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Sede lavorativa non trovata con id " + request.workLocationId()));

        EmployeeEntity employee = new EmployeeEntity(request.name(), request.surname(), request.email(),
                request.dateOfBirth());
        employee.setWorkLocation(workLocation);
        employee.setEmployeeStatus(EmployeeStatus.ACTIVE);
        employee.setTeams(resolveTeams(request.teams()));

        return toResponseDTO(employeeRepository.save(employee));
    }
	
	public Optional<EmployeeResponseDTO> changeStatus(Long id, EmployeeStatusUpdateRequestDTO request) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setEmployeeStatus(request.status());
                    return toResponseDTO(employeeRepository.save(employee));
                });
    }
	
	public void deleteEmployee(Long employeeId) {
		EmployeeEntity employeeEntity = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new IllegalArgumentException("Impossibile cancellare l'utente con id " + employeeId ));
		employeeRepository.delete(employeeEntity);
	}

	private Set<TeamEntity> resolveTeams(Set<Long> teamIds) {
		if (teamIds == null || teamIds.isEmpty()) {
			return new HashSet<>();
		}
		return new HashSet<>(teamRepository.findAllById(teamIds));
	}

	private EmployeeResponseDTO toResponseDTO(EmployeeEntity employee) {
		String fullName = employee.getName() + " " + employee.getSurname();
		String workLocationName = employee.getWorkLocation() != null ? employee.getWorkLocation().getCity() : null;
		List<String> teamNames = employee.getTeams().stream().map(TeamEntity::getName).toList();

		return new EmployeeResponseDTO(employee.getEmployeeId(), fullName, employee.getEmail(), workLocationName,
				teamNames);
	}

}
