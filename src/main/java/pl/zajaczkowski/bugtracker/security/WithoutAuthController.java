package pl.zajaczkowski.bugtracker.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WithoutAuthController {

    @GetMapping("/about")
    String about(){
        return "about";
    }

    @GetMapping("/")
    String layout() {
        return "layouts/no_auth_layout";
    }
}
