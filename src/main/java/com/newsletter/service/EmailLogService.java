package com.newsletter.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.newsletter.entity.EmailLogEntity;
import com.newsletter.entity.EmailLogStatus;
import com.newsletter.entity.EmployeeEntity;
import com.newsletter.entity.EmployeeStatus;
import com.newsletter.entity.WorkLocationEntity;
import com.newsletter.repository.EmailLogRepository;
import com.newsletter.repository.EmployeeRepository;

@Service
public class EmailLogService {

	private static final Logger log = LoggerFactory.getLogger(EmailLogService.class);
	private final EmailLogRepository emailLogRepository;
	private final EmployeeRepository employeeRepository;
	private final JavaMailSender mailSender;

	private String emailSubject = "Tanti Auguri di Buon Compleanno!";
	private String emailTemplate = "Oggi festeggiamo il compleanno dei seguenti colleghi: {celebrants}. Auguri!";

	public EmailLogService(EmailLogRepository emailLogRepository, EmployeeRepository employeeRepository,
			JavaMailSender mailSender) {
		this.emailLogRepository = emailLogRepository;
		this.employeeRepository = employeeRepository;
		this.mailSender = mailSender;
	}

	public int sendHappyBirthayEmails() {
		LocalDate today = LocalDate.now();

		List<EmployeeEntity> employeeActive = employeeRepository.findAll().stream()
				.filter(employee -> employee.getEmployeeStatus().equals(EmployeeStatus.ACTIVE)).toList();

		List<EmployeeEntity> celebratedList = employeeActive.stream()
		        .filter(employee -> isBirthdayToday(employee.getDateOfBirth(), today))
		        .toList();

		if (celebratedList.isEmpty()) {
			log.info("Nessun festeggiato");
			return 0;
		}

		Map<WorkLocationEntity, List<EmployeeEntity>> celebrantsByLocation = celebratedList.stream()
				.filter(e -> e.getWorkLocation() != null)
				.collect(Collectors.groupingBy(EmployeeEntity::getWorkLocation));

		int countEmail = 0;

		for (Map.Entry<WorkLocationEntity, List<EmployeeEntity>> entry : celebrantsByLocation.entrySet()) {
			WorkLocationEntity location = entry.getKey();
			List<EmployeeEntity> locationCelebrants = entry.getValue();

			// Seleziona i destinatari attivi della stessa sede
			List<EmployeeEntity> locationRecipients = employeeActive.stream()
					.filter(e -> Objects.equals(e.getWorkLocation(), location)).toList();

			String bodyContent = getBodyContent(locationCelebrants, today);

			for (EmployeeEntity recipient : locationRecipients) {
				try {
					SimpleMailMessage message = new SimpleMailMessage();
					String emailRecipient = recipient.getEmail();
					message.setTo(emailRecipient);
					message.setSubject(emailSubject);
					message.setText(bodyContent);
					mailSender.send(message);

					log.info("Email inviata " + emailRecipient);
					countEmail = countEmail + 1;
					emailLogRepository.save(new EmailLogEntity(emailRecipient, emailSubject, EmailLogStatus.SENT));
					log.info("Email salvata " + emailRecipient);
				
				} catch (Exception e) {
					log.error("Errore nell'invio a {}: {}", recipient.getEmail(), e.getMessage());
					emailLogRepository
							.save(new EmailLogEntity(recipient.getEmail(), emailSubject, EmailLogStatus.FAILED));
				}
			}
		}

		return countEmail;
	}

	public List<EmailLogEntity> getEmailHistory() {
		return emailLogRepository.findAll();
	}

	private String getBodyContent(List<EmployeeEntity> celebrantsList, LocalDate today) {
		String celebrantsText = celebrantsList.stream().map(
				e -> e.getName() + " " + e.getSurname() + " (" + howOldAreYou(today, e.getDateOfBirth()) + " anni)")
				.collect(Collectors.joining(", "));

		String bodyContent = emailTemplate.replace("{celebrants}", celebrantsText);
		return bodyContent;
	}

	private int howOldAreYou(LocalDate today, LocalDate birthDate) {
		return java.time.Period.between(birthDate, today).getYears();
	}

	public void updateTemplate(String newSubject, String newTemplateBody) {
		this.emailSubject = newSubject;
		this.emailTemplate = newTemplateBody;
	}
	
	private boolean isBirthdayToday(LocalDate birthDate, LocalDate today) {
	    // Caso speciale: Nato il 29 febbraio in anno non bisestile -> festeggia il 28 febbraio
	    if (birthDate.getMonthValue() == 2 && birthDate.getDayOfMonth() == 29 && !today.isLeapYear()) {
	        return today.getMonthValue() == 2 && today.getDayOfMonth() == 28;
	    }

	    // Confronto standard giorno e mese
	    return birthDate.getMonth() == today.getMonth() 
	        && birthDate.getDayOfMonth() == today.getDayOfMonth();
	}

}
