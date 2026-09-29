package com.newsletter.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsletter.entity.EmailLogEntity;
import com.newsletter.service.EmailLogService;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    private final EmailLogService emailService;

    public EmailController(EmailLogService emailService) {
        this.emailService = emailService;
    }

    // POST /api/emails/send-birthdays -> Avvia manualmente l'invio delle mail di compleanno
    @PostMapping("/send-birthdays")
    public ResponseEntity<Map<String, Object>> sendBirthdays() {
        int emailsSent = emailService.sendHappyBirthayEmails();
        return ResponseEntity.ok(Map.of(
                "message", "Processo di invio email eseguito con successo",
                "emailsSent", emailsSent
        ));
    }

    // GET /api/emails/logs -> Restituisce la cronologia dei log salvati
    @GetMapping("/logs")
    public ResponseEntity<List<EmailLogEntity>> getEmailLogs() {
        return ResponseEntity.ok(emailService.getEmailHistory());
    }
    
    @PutMapping("/template")
    public ResponseEntity<Map<String, String>> updateTemplate(@RequestBody Map<String, String> payload) {
        String subject = payload.get("subject");
        String template = payload.get("template");
        
        emailService.updateTemplate(subject, template);
        return ResponseEntity.ok(Map.of("message", "Template aggiornato correttamente"));
    }

}