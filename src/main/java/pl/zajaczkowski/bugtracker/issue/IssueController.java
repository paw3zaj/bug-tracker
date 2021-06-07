package pl.zajaczkowski.bugtracker.issue;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.zajaczkowski.bugtracker.auth.AuthorityName;
import pl.zajaczkowski.bugtracker.auth.Person;
import pl.zajaczkowski.bugtracker.auth.PersonService;
import pl.zajaczkowski.bugtracker.mail.MailService;
import pl.zajaczkowski.bugtracker.project.Project;
import pl.zajaczkowski.bugtracker.project.ProjectService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;
    private final ProjectService projectService;
    private final PersonService personService;
    private final MailService mailService;

    public IssueController(IssueService issueService, ProjectService projectService, PersonService personService, MailService mailService) {
        this.issueService = issueService;
        this.projectService = projectService;
        this.personService = personService;
        this.mailService = mailService;
    }

    @ModelAttribute("assignees")
    public Iterable<Person> getAssignees() {
        return personService.findAllPersons();
    }

    @ModelAttribute("projects")
    public Iterable<Project> getProjects() {
        return projectService.findAllProjects();
    }

    @ModelAttribute("priorities")
    public Iterable<Priority> getPriorities() {
        return issueService.findAllPriorities();
    }

    @ModelAttribute("statuses")
    public Iterable<Status> getStatuses() {
        return issueService.findAllStatuses();
    }

    @ModelAttribute("types")
    public Iterable<Type> getTypes(){
        return issueService.findAllTypes();
    }

    @GetMapping
    String showIssueList(@ModelAttribute IssueFilter issueFilter, Principal principal, Model model) {
        var login = principal.getName();
        var access = personService.checkAccess(login, AuthorityName.ROLE_MANAGE_PROJECT);
        var permission = personService.hasPermission(login);

        model.addAttribute("issues", issueService.findAllIssues(issueFilter));
        model.addAttribute("access", access);
        model.addAttribute("permission", permission);
        model.addAttribute("currentPage", "issues");
        model.addAttribute("filter", issueFilter);
        return "issue/issues";
    }

    @GetMapping("/add")
    @Secured("ROLE_MANAGE_PROJECT")
    String showAdd(Principal principal, Model model) {
        var permission = personService.hasPermission(principal.getName());

        model.addAttribute("currentPage", "issues");
        model.addAttribute("permission", permission);
        model.addAttribute("issue", new Issue());
        return "issue/add";
    }

    @GetMapping("/edit")
    @Secured("ROLE_MANAGE_PROJECT")
    public String showUpdate(@RequestParam Long id, Principal principal, Model model) {
        Issue issue = issueService.findIssueById(id).orElse(null);
        if (issue == null) {
            return "redirect:/issues";
        }
        var permission = personService.hasPermission(principal.getName());

        model.addAttribute("currentPage", "issues");
        model.addAttribute("permission", permission);
        model.addAttribute("issue", issue);
        return "issue/add";
    }

    @PostMapping("/save")
    @Secured("ROLE_MANAGE_PROJECT")
    public String save(@Valid Issue issue, BindingResult result, Principal principal, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("issue", issue);
            return "issue/add";
        }
        var optionalUser = personService.findPersonByLogin(principal.getName());
        optionalUser.ifPresent(issue::setCreator);

        issueService.saveIssue(issue);
        return "redirect:/issues";
    }

    @GetMapping("/remove")
    @Secured("ROLE_MANAGE_PROJECT")
    public String delete(@RequestParam Long id) {
        var optionalIssue = issueService.findIssueById(id);
        optionalIssue.ifPresent(issue -> {

            var recipient = issue.getCreator().getEmail();
            if(recipient != null && recipient.length() != 0) {
                var mail = issueService.prepareMail(issue);
                mail.setRecipient(recipient);
                mailService.send(mail);
            }
            issueService.deleteIssue(id);

        });
        return "redirect:/issues";
    }
}
