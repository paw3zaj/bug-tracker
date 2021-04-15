package pl.zajaczkowski.bugtracker;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    @Secured("ROLE_USER_TAB")
    String index() {
        return "index";
    }

    @GetMapping("/about")
    String about(){
        return "about";
    }
}
