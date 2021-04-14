package pl.zajaczkowski.bugtracker.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping
    String login() {
        return "index";
    }
}
