package pl.zajaczkowski.bugtracker.issue;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zajaczkowski.bugtracker.auth.AuthorityName;
import pl.zajaczkowski.bugtracker.auth.Person;
import pl.zajaczkowski.bugtracker.project.Project;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping
    @Secured("ROLE_MANAGE_PROJECT")
    String showIssueList(Model model) {
        Iterable<Issue> issues = issueService.findAllIssues();
        model.addAttribute("issues", issues);
        return "issue/issues";
    }

    @GetMapping("/add")
    @Secured("ROLE_MANAGE_PROJECT")
    String showAdd(Model model) {
        Iterable<Priority> priorities = issueService.findAllPriorities();
        Iterable<Status> statuses = issueService.findAllStatuses();
        Iterable<Type> types = issueService.findAllTypes();
        Iterable<Person> managers = issueService.
                findAllManagers(AuthorityName.ROLE_MANAGE_PROJECT);
        Iterable<Person> assignees = issueService.findAllPersons();
        Iterable<Project> projects = issueService.findAllProject();
        model.addAttribute("managers", managers);
        model.addAttribute("assignees", assignees);
        model.addAttribute("projects", projects);
        model.addAttribute("priorities", priorities);
        model.addAttribute("statuses", statuses);
        model.addAttribute("types", types);
        model.addAttribute("issue", new Issue());
        return "issue/add";
    }

    @GetMapping("/edit")
    @Secured("ROLE_MANAGE_PROJECT")
    public String showUpdate(@RequestParam Long id, Model model) {
        Issue issue = issueService.findIssueById(id).orElse(null);
        if(issue == null) {
            return "redirect:/issues";
        }

        Iterable<Priority> priorities = issueService.findAllPriorities();
        Iterable<Status> statuses = issueService.findAllStatuses();
        Iterable<Type> types = issueService.findAllTypes();
        Iterable<Person> managers = issueService.
                findAllManagers(AuthorityName.ROLE_MANAGE_PROJECT);
        Iterable<Person> assignees = issueService.findAllPersons();
        Iterable<Project> projects = issueService.findAllProject();
        model.addAttribute("managers", managers);
        model.addAttribute("assignees", assignees);
        model.addAttribute("projects", projects);
        model.addAttribute("priorities", priorities);
        model.addAttribute("statuses", statuses);
        model.addAttribute("types", types);
        model.addAttribute("issue", issue);
        return "issue/add";
    }

    @PostMapping("/save")
    @Secured("ROLE_MANAGE_PROJECT")
    public String save(@Valid Issue issue, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "redirect:/issues/add";
        }
        var optionalUser = issueService.getLoggedUser(principal);
        optionalUser.ifPresent(issue::setCreator);

        issueService.saveIssue(issue);
        return "redirect:/issues";
    }
}
