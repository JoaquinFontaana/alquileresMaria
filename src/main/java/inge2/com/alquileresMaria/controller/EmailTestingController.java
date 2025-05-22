package inge2.com.alquileresMaria.controller;

import inge2.com.alquileresMaria.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailTestingController {

    @Autowired
    private EmailService emailService;

    public EmailTestingController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping("/send-test-email")
    public void sentEmailTest(){
        emailService.sendEmail("test@example.com","Email test","putooo");
    }
}
