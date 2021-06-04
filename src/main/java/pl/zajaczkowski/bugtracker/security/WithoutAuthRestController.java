package pl.zajaczkowski.bugtracker.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zajaczkowski.bugtracker.project.Project;
import pl.zajaczkowski.bugtracker.project.ProjectService;

@RestController
@RequestMapping("/api")
public class WithoutAuthRestController {

    private final ProjectService service;

    public WithoutAuthRestController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    public Project fakeMethod() {

        Long id = 0L;
        var project = service.findProjectById(id).orElse(new Project());
        return project;
    }
}
