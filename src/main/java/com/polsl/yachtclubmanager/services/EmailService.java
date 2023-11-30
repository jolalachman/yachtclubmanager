package com.polsl.yachtclubmanager.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.polsl.yachtclubmanager.utils.EmailUtils.*;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;
    private final JavaMailSender emailSender;
    public void sendAccountVerificationMail(String to, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("New User Account Verification");
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(getEmailMessage(token));
            emailSender.send(message);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public void sendResetPasswordMail(String to, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("User Reset Password");
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(getResetPasswordMessage(token));
            emailSender.send(message);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
