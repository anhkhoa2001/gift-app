package personal.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MailApplication {

    public static void main(String[] args) {
        new SpringApplication(MailApplication.class).run(args);
    }
}
