package edu.harvard.h2ms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service("emailService")
@PropertySources({
	@PropertySource(value = "classpath:mailserver.properties.template", ignoreResourceNotFound = true),
	@PropertySource(value = "classpath:mailserver.mock.properties", ignoreResourceNotFound = true),
	@PropertySource(value = "classpath:mailserver.properties", ignoreResourceNotFound = true)
	
})

public class EmailServiceImpl implements EmailService {
	
	final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	// Async will not wait for email delivery
	@Async
	public void sendEmail(SimpleMailMessage email) {
		
		
		log.info("sending email to: "+email);
		mailSender.send(email);
	}

}
