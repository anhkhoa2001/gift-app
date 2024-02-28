package personal.example.service;

import personal.example.dto.Notification;

import java.util.List;

public interface PushService {

    void sendToKafka(String topic, Notification notification);

}
