package personal.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personal.example.dto.Notification;
import personal.example.dto.UserDTO;
import personal.example.repo.UserRepo;
import personal.example.service.PushService;
import personal.example.service.UserService;
import personal.example.utils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final PushService pushService;

    ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PushService pushService) {
        this.userRepo = userRepo;
        this.pushService = pushService;
    }

    @Override
    public void handleUser() {
        try {
            Map<String, List<UserDTO>> users = userRepo.getAllUser();

            List<UserDTO> top1 = users.getOrDefault("TOP1", new ArrayList<>());
            top1.forEach(e -> {
                Notification notification = new Notification(e);
                pushService.sendToKafka(Constant.LOW_BANDWIDTH, notification);
            });


            List<UserDTO> top2 = users.getOrDefault("TOP2", new ArrayList<>());

            top2.forEach(e -> {
                Notification notification = new Notification(e);
                pushService.sendToKafka(Constant.LOW_BANDWIDTH, notification);
            });
            List<UserDTO> top3 = users.getOrDefault("TOP3", new ArrayList<>());

            top3.forEach(e -> {
                Notification notification = new Notification(e);
                pushService.sendToKafka(Constant.LOW_BANDWIDTH, notification);
            });
            List<UserDTO> remain = users.getOrDefault("TOP4", new ArrayList<>());

            remain.forEach(e -> {
                executor.submit(() -> {
                    int index = e.getUser_id() % Constant.TOTAL_CHILD_CLUSTER;
                    String topic = Constant.HIGH_BANDWIDTH + "-" + index;
                    Notification notification = new Notification(e);
                    pushService.sendToKafka(topic, notification);
                });
            });

            executor.shutdown();
            log.info("=====================DONE=================");
        } catch (Exception e) {
            log.error("{}", e.getClass());
        }
    }

    /*@Bean
    public void init() {
        long begin = System.currentTimeMillis();
        handleUser();
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }*/
}
