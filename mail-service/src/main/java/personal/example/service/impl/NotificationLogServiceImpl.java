package personal.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personal.example.model.NotificationLog;
import personal.example.repo.NotificationLogRepo;
import personal.example.service.NotificationLogService;

@Service
public class NotificationLogServiceImpl implements NotificationLogService {

    private final NotificationLogRepo notificationLogRepo;

    @Autowired
    public NotificationLogServiceImpl(NotificationLogRepo notificationLogRepo) {
        this.notificationLogRepo = notificationLogRepo;
    }

    @Override
    public void saveLog(NotificationLog notificationLog) {
        notificationLogRepo.save(notificationLog);
    }
}
