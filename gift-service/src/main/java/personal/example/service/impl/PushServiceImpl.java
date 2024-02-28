package personal.example.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import personal.example.dto.Notification;
import personal.example.service.PushService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class PushServiceImpl implements PushService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public PushServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToKafka(String topic, Notification notification) {
        try {
            String payload = mapper.writeValueAsString(notification);
            ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, payload);

            SendResult<String, String> send = result.get();

            log.info("result {}", send);
        } catch (JsonProcessingException e) {
            log.error("Notification sent failed => {}", notification);
        } catch (ExecutionException | InterruptedException e) {
            log.error("Notification push to topic {} failed => {}", topic, notification);
        }
    }
}
