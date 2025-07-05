
package com.ab.smbe.controller;

import com.ab.smbe.dto.Contact;
import com.ab.smbe.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/email")
    public ResponseEntity<String> saveEmail(@RequestBody Contact contact) {
        try {
            String result = contactService.sendEmail(contact);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Failed to process email request: " + e.getMessage());
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> healthStatus = new HashMap<>();
        healthStatus.put("status", "UP");
        healthStatus.put("timestamp", LocalDateTime.now().toString());
        healthStatus.put("service", "smbe");

        // Add basic system information
        healthStatus.put("javaVersion", System.getProperty("java.version"));
        healthStatus.put("availableProcessors", Runtime.getRuntime().availableProcessors());
        healthStatus.put("freeMemory", Runtime.getRuntime().freeMemory());
        healthStatus.put("totalMemory", Runtime.getRuntime().totalMemory());

        return ResponseEntity.ok(healthStatus);
    }



}