package com.newsletter.config;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.newsletter.entity.EmployeeEntity;
import com.newsletter.entity.EmployeeStatus;
import com.newsletter.entity.TeamEntity;
import com.newsletter.entity.WorkLocationEntity;
import com.newsletter.repository.EmailLogRepository;
import com.newsletter.repository.EmployeeRepository;
import com.newsletter.repository.TeamRepository;
import com.newsletter.repository.WorkLocationRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EmailLogRepository emailLogRepository;
    private final EmployeeRepository employeeRepository;
    private final TeamRepository teamRepository;
    private final WorkLocationRepository workLocationRepository;

    public DataInitializer(
            EmailLogRepository emailLogRepository,
            EmployeeRepository employeeRepository,
            TeamRepository teamRepository,
            WorkLocationRepository workLocationRepository) {
        this.emailLogRepository = emailLogRepository;
        this.employeeRepository = employeeRepository;
        this.teamRepository = teamRepository;
        this.workLocationRepository = workLocationRepository;
    }

    @Override
    public void run(String... args) {
        // 1. Pulizia tabelle
        emailLogRepository.deleteAll();
        employeeRepository.deleteAll();
        teamRepository.deleteAll();
        workLocationRepository.deleteAll();

        // 2. Creazione Sedi tramite Repository
        WorkLocationEntity locRoma = new WorkLocationEntity();
        locRoma.setCity("Roma");
        locRoma.setAddress("Via Nazionale 10");
        locRoma = workLocationRepository.save(locRoma);

        WorkLocationEntity locMilano = new WorkLocationEntity();
        locMilano.setCity("Milano");
        locMilano.setAddress("Via Dante 5");
        locMilano = workLocationRepository.save(locMilano);

        // 3. Creazione Team tramite Repository
        TeamEntity teamDev = teamRepository.save(new TeamEntity("Sviluppo"));
        TeamEntity teamMkt = teamRepository.save(new TeamEntity("Marketing"));

        // 4. Creazione Dipendenti tramite Repository
        LocalDate today = LocalDate.now();

        // Festeggia oggi (Roma)
        EmployeeEntity emp1 = new EmployeeEntity("Mario", "Rossi", "mario.rossi@example.com", today.minusYears(30));
        emp1.setEmployeeStatus(EmployeeStatus.ACTIVE);
        emp1.setWorkLocation(locRoma);
        emp1.setTeams(Set.of(teamDev));
        employeeRepository.save(emp1);

        // Stessa sede di Mario (Roma)
        EmployeeEntity emp2 = new EmployeeEntity("Luigi", "Verdi", "luigi.verdi@example.com", LocalDate.of(1992, 5, 15));
        emp2.setEmployeeStatus(EmployeeStatus.ACTIVE);
        emp2.setWorkLocation(locRoma);
        emp2.setTeams(Set.of(teamDev, teamMkt));
        employeeRepository.save(emp2);

        // Sede diversa (Milano)
        EmployeeEntity emp3 = new EmployeeEntity("Giulia", "Bianchi", "giulia.bianchi@example.com", LocalDate.of(1995, 8, 20));
        emp3.setEmployeeStatus(EmployeeStatus.ACTIVE);
        emp3.setWorkLocation(locMilano);
        emp3.setTeams(Set.of(teamMkt));
        employeeRepository.save(emp3);
    }
}