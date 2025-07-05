
package com.ab.smbe.service;

import com.ab.smbe.dto.Contact;
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
            sendAckEmail(contact);
            return "Email sent successfully";
        } catch (Exception e) {
            return "Failed to send email: " + e.getMessage();
        }
    }

    public void sendAckEmail(Contact contact) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(contact.email());
            message.setSubject("Thank you for contacting us!");
            message.setText("""
            Dear %s,
            
            Thank you for reaching out to us. We have received your message and appreciate you taking the time to contact us.
            
            Our team will review your message and get back to you as soon as possible. We typically respond within 1-2 business days.
            
            If you have any urgent concerns, please don't hesitate to call our customer service team.
            
            Best regards,
            The Support Team
            """.formatted(
                    contact.name()
            ));


            emailSender.send(message);
        } catch (Exception e) {
        }
    }
}