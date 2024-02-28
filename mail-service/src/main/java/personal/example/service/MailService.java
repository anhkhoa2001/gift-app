package personal.example.service;

import personal.example.dto.Notification;

public interface MailService {

    void sendMail(Notification notification) throws InterruptedException;
}
