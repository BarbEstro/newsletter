package com.newsletter.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MockMailConfig {

    private static final Logger log = LoggerFactory.getLogger(MockMailConfig.class);

    @Bean
    @Primary
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl() {
            @Override
            public void send(SimpleMailMessage simpleMessage) {
                log.info("================ MOCK EMAIL INVIATA ================");
                log.info("A: {}", (Object) simpleMessage.getTo());
                log.info("Oggetto: {}", simpleMessage.getSubject());
                log.info("Testo: {}", simpleMessage.getText());
                log.info("====================================================");
            }
        };
    }
}