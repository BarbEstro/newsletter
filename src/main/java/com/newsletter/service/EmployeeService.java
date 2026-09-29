package com.newsletter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.newsletter.entity.EmployeeEntity;
import com.newsletter.entity.EmployeeStatus;
import com.newsletter.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	private final EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	public List<EmployeeEntity> getAllEmployees() {
        return employeeRepository.findAll();
    }
	
	public List<EmployeeEntity> getActiveEmployees() {
        return employeeRepository.findAll().stream()
                .filter(emp -> EmployeeStatus.ACTIVE.equals(emp.getEmployeeStatus()))
                .toList();
    }
	
	public Optional<EmployeeEntity> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
	
	public EmployeeEntity registerEmployee(EmployeeEntity employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("Impossibile registrare: l'email " + employee.getEmail() + " esiste già.");
        }

        if (employee.getEmployeeStatus() == null) {
            employee.setEmployeeStatus(EmployeeStatus.ACTIVE);
        }

        return employeeRepository.save(employee);
    }
	
	public Optional<EmployeeEntity> changeStatus(Long id, EmployeeStatus status) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setEmployeeStatus(status);
                    return employeeRepository.save(employee);
                });
    }
	
	public void deleteEmployee(Long employeeId) {
		EmployeeEntity employeeEntity = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new IllegalArgumentException("Impossibile cancellare l'utente con id " + employeeId ));
		employeeRepository.delete(employeeEntity);
	}

}
