package pl.zajaczkowski.bugtracker.project;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zajaczkowski.bugtracker.auth.AuthorityName;
import pl.zajaczkowski.bugtracker.auth.PersonService;
import pl.zajaczkowski.bugtracker.issue.IssueService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final IssueService issueService;
    private final PersonService personService;

    public ProjectController(ProjectService projectService, IssueService issueService, PersonService personService) {
        this.projectService = projectService;
        this.issueService = issueService;
        this.personService = personService;
    }

    @GetMapping
    String showProjectsList(Principal principal, Model model) {
        var login = principal.getName();
        var access = personService.checkAccess(login, AuthorityName.ROLE_MANAGE_PROJECT);
        var permission = personService.hasPermission(login);

        model.addAttribute("projects", projectService.findAllProjects());
        model.addAttribute("access", access);
        model.addAttribute("permission", permission);
        model.addAttribute("currentPage", "projects");
        return "project/projects";
    }

    @GetMapping("/add")
    @Secured("ROLE_MANAGE_PROJECT")
    String showAdd(Principal principal, Model model) {
        var permission = personService.hasPermission(principal.getName());

        model.addAttribute("currentPage", "projects");
        model.addAttribute("permission", permission);
        model.addAttribute("project", new Project());
        return "project/add";
    }

    @GetMapping("/edit")
    @Secured("ROLE_MANAGE_PROJECT")
    public String showUpdate(@RequestParam Long id, Principal principal, Model model) {
        var project = projectService.findProjectById(id).orElse(null);
        if (project == null) {
            return "redirect:/projects";
        }
        var permission = personService.hasPermission(principal.getName());

        model.addAttribute("currentPage", "projects");
        model.addAttribute("permission", permission);
        model.addAttribute("project", project);
        return "project/add";
    }

    @PostMapping("/save")
    @Secured("ROLE_MANAGE_PROJECT")
    public String save(@Valid Project project, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("project", project);
            return "project/add";
        }
        projectService.saveProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/remove")
    @Secured("ROLE_MANAGE_PROJECT")
    public String remove(@RequestParam Long id) {

        var optionalProject = projectService.findProjectById(id);
        optionalProject.ifPresent(project -> {
            var issues = issueService.findAllIssuesByProject(project);
            issueService.deleteIssuesList(issues);
            projectService.deleteProject(id);
        });

        return "redirect:/projects";
    }
}
