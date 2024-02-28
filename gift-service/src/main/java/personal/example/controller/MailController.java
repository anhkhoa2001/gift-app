package personal.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.example.service.UserService;

@RestController
@RequestMapping("/mail")
@Slf4j
public class MailController {

    private final UserService userService;

    @Autowired
    public MailController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity handle() {
        userService.handleUser();
        return ResponseEntity.ok("DONE!!");
    }

}
