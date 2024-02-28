package personal.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import personal.example.service.UserService;

@SpringBootApplication
public class GiftApplication {

    public static void main(String[] args) {
        new SpringApplication(GiftApplication.class).run(args);
    }

}