package rs.travel.bookingWithEase.service;

import java.util.Properties;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
	@Autowired
	private JavaMailSenderImpl javaMailSender;

	/*
	 * Koriscenje klase za ocitavanje vrednosti iz application.properties fajla
	 */

	@Async
	public void sendEmail(SimpleMailMessage email) throws MessagingException {

		Properties mailProperties = System.getProperties();
		mailProperties.setProperty("mail.transport.protocol", "smtp");     
		mailProperties.setProperty("mail.host", "smtp.gmail.com"); 
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.smtp.starttls.required","true");
        mailProperties.put("mail.smtp.socketFactory.port", "587");
        mailProperties.put("mail.smtp.debug", "true");
        //mailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
       // mailProperties.put("mail.smtp.socketFactory.fallback", fallback);

        javaMailSender.setJavaMailProperties(mailProperties);
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setProtocol("smtp");
        javaMailSender.setUsername("bookingwithease19@gmail.com");
        javaMailSender.setPassword("bookingwithease");
		javaMailSender.send(email);
	}
}
