package pl.zajaczkowski.bugtracker;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/dashboard")
    @Secured("ROLE_USER_TAB")
    String dashboard() {
        return "dashboard";
    }

    @GetMapping("/about")
    String about(){
        return "about";
    }

    @GetMapping("/")
    String layout() {
        return "layouts/no_auth_layout";
    }

    @GetMapping("/signup")
    String signup() {
        return "security/sign_up";
    }
}
