package pl.zajaczkowski.bugtracker.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    String login() {
        return "security/login";
    }

    @GetMapping
    String index() {
        return "index";
    }

    @GetMapping("/about")
    String about(){
        return "about";
    }

}
