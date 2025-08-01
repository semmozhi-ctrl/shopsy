package com.kartblaze.shopsy.services;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
