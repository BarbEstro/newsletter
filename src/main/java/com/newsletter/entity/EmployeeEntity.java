package com.newsletter.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class EmployeeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String surname;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private LocalDate dateOfBirth;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EmployeeStatus employeeStatus;
	
	@ManyToOne
	@JoinColumn(name = "workLocationId")
	private WorkLocationEntity workLocation;
	
	@ManyToMany
	@JoinTable(
	    name = "employee_team", // Nome della tabella di congiunzione nel DB
	    joinColumns = @JoinColumn(name = "employeeId"), // FK verso la tabella employee
	    inverseJoinColumns = @JoinColumn(name = "teamId") // FK verso la tabella team
	)
	private Set<TeamEntity> teams = new HashSet<>();

	public EmployeeEntity() {
	}

	public EmployeeEntity(String name, String surname, String email, LocalDate dateOfBirth) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public EmployeeStatus getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(EmployeeStatus employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public WorkLocationEntity getWorkLocation() {
		return workLocation;
	}

	public void setWorkLocation(WorkLocationEntity workLocation) {
		this.workLocation = workLocation;
	}

	public Set<TeamEntity> getTeams() {
		return teams;
	}

	public void setTeams(Set<TeamEntity> teams) {
		this.teams = teams;
	}
	
	
}
