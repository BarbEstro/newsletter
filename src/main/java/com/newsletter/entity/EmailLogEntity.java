package com.newsletter.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "email_log")
public class EmailLogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long emailLogId;

	@Column(nullable = false)
	private LocalDateTime sentAt;

	@Column(nullable = false)
	private String recipient;

	@Column(nullable = false)
	private String subject;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EmailLogStatus status; 

	public EmailLogEntity() {
	}

	public EmailLogEntity(String recipient, String subject, EmailLogStatus status) {
		this.sentAt = LocalDateTime.now();
		this.recipient = recipient;
		this.subject = subject;
		this.status = status;
	}

	public Long getEmailLogId() {
		return emailLogId;
	}

	public void setEmailLogId(Long emailLogId) {
		this.emailLogId = emailLogId;
	}

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public EmailLogStatus getStatus() {
		return status;
	}

	public void setStatus(EmailLogStatus status) {
		this.status = status;
	}

}