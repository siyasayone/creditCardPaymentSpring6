package com.example.creditCardBillPaymentspring.springframework3.utils.email.smtp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SMTPConfig {

	static @Bean public SMTPBroker getSMTPBroker() {
		return new SMTPBroker();
	}
}
