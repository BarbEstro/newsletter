package com.newsletter.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.newsletter.service.EmailLogService;

@Component
public class BirthdayScheduler {
	private static final Logger log = LoggerFactory.getLogger(BirthdayScheduler.class);
    private final EmailLogService emailLogService;

    public BirthdayScheduler(EmailLogService emailLogService) {
        this.emailLogService = emailLogService;
    }

    // Esegue ogni giorno alle 09:00 del mattino
    @Scheduled(cron = "0 0 9 * * ?")
    public void runDailyBirthdayEmails() {
        log.info("Avvio automatico dello scheduler per le email di compleanno...");
        int sent = emailLogService.sendHappyBirthayEmails();
        log.info("Scheduler completato. Email inviate: {}", sent);
    }
}
