package personal.example.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import personal.example.dto.Notification;
import personal.example.model.NotificationLog;
import personal.example.service.MailService;
import personal.example.service.NotificationLogService;
import personal.example.utils.Constant;

@Component
@Slf4j
public class NotiListener {

    private final NotificationLogService notificationLogService;
    private final MailService mailService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public NotiListener(NotificationLogService notificationLogService, MailService mailService) {
        this.notificationLogService = notificationLogService;
        this.mailService = mailService;
    }

    @KafkaListener(topics = Constant.LOW_BANDWIDTH, groupId = "low_group",
            containerFactory = "lowKafkaListenerContainerFactory")
    @Transactional
    public void onListenerLow(@Payload String payload,
                           Acknowledgment acknowledgment) {
        handleMessage(payload);
        acknowledgment.acknowledge();
    }


    @KafkaListener(topics = {Constant.HIGH_BANDWIDTH + "-" + 0},
                groupId = "high_group",
                containerFactory = "lowKafkaListenerContainerFactory")
    public void onListenerHigh(@Payload String payload,
                               Acknowledgment acknowledgment) {
        handleMessage(payload);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = {Constant.HIGH_BANDWIDTH + "-" + 1},
            groupId = "high_group1",
            containerFactory = "lowKafkaListenerContainerFactory")
    public void onListenerHigh1(@Payload String payload,
                               Acknowledgment acknowledgment) {
        handleMessage(payload);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = {Constant.HIGH_BANDWIDTH + "-" + 2},
            groupId = "high_group2",
            containerFactory = "lowKafkaListenerContainerFactory")
    public void onListenerHigh2(@Payload String payload,
                               Acknowledgment acknowledgment) {
        handleMessage(payload);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = {Constant.HIGH_BANDWIDTH + "-" + 3},
            groupId = "high_group3",
            containerFactory = "lowKafkaListenerContainerFactory")
    public void onListenerHigh3(@Payload String payload,
                                Acknowledgment acknowledgment) {
        handleMessage(payload);
        acknowledgment.acknowledge();
    }
    private void handleMessage(String payload) {
        long begin = System.currentTimeMillis();
        String response = null;
        boolean status = true;
        try {
            Notification notification = objectMapper.readValue(payload, Notification.class);
            mailService.sendMail(notification);

            response = "DONE";
        } catch (Exception e) {
            status = false;
            response = e.getClass() + "|" + e.getMessage();
            //nếu send mail failed hoặc đọc bản tin từ MQ failed hoặc bị giới hạn băng thông thì reason sẽ được ghi vào log
            //tùy vào từng lỗi sẽ xử lí riêng biệt
            //giới hạn băng thông: retry sau 1 khoảng thời gian
            //send failed: retry thủ công thông qua reason ở log
            //...
        }

        try {
            long done = System.currentTimeMillis();

            NotificationLog notificationLog = new NotificationLog(payload, response, done - begin, status);
            notificationLogService.saveLog(notificationLog);
        } catch (Exception e) {
            throw e;
            //nếu việc save log failed
            //call api tới gift-service
            //api gift service sẽ đẩy message lại topic low-bandwidth
        }
    }
}
