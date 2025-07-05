
package com.ab.smbe.service;

import com.ab.smbe.dto.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final JavaMailSender emailSender;

    @Value( "${spring.mail.username}")
    private String sendMailTo;

    public ContactService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public String sendEmail(Contact contact) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(sendMailTo);
            message.setSubject("Contact Form Submission");
            message.setText("""
                Name: %s
                Email: %s
                Phone: %s
                Message: %s
                """.formatted(
                    contact.name(),
                    contact.email(),
                    contact.phone(),
                    contact.message()
            ));

            emailSender.send(message);
            return "Email sent successfully";
        } catch (Exception e) {
            return "Failed to send email: " + e.getMessage();
        }
    }
}