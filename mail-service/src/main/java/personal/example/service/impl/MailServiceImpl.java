package personal.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import personal.example.dto.Notification;
import personal.example.service.MailService;

import java.util.Date;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Override
    public void sendMail(Notification notification) throws InterruptedException {
        log.info("content {}", notification);
        //logic send mail
        //gia su viec gui 1 mail mat 1s
        Thread.sleep(1000);
    }
}
