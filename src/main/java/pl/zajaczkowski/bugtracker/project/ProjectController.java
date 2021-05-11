package pl.zajaczkowski.bugtracker.project;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/add")
    @Secured("ROLE_MANAGE_PROJECT")
    String showAdd(Model model) {
        model.addAttribute("project", new Project());
        return "project/add";
    }

    @PostMapping("/addProject")
    @Secured("ROLE_MANAGE_PROJECT")
    public String addProject(Project project, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/projects/add";
        }
        projectService.saveProject(project);
        return "redirect:/projects";
    }
}
