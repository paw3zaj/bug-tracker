package pl.zajaczkowski.bugtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class BugTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BugTrackerApplication.class, args);
    }

}
