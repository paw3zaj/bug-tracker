package pl.zajaczkowski.bugtracker.issue;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    String showIssueList(@ModelAttribute IssueFilter issueFilter, Model model) {
        prepareModel(model, false);
        model.addAttribute("issues", issueService.findAllIssues(issueFilter));
        model.addAttribute("filter", issueFilter);
        return "issue/issues";
    }

    @GetMapping("/add")
    @Secured("ROLE_MANAGE_PROJECT")
    String showAdd(Model model) {
        prepareModel(model);
        model.addAttribute("issue", new Issue());
        return "issue/add";
    }

    @GetMapping("/edit")
    @Secured("ROLE_MANAGE_PROJECT")
    public String showUpdate(@RequestParam Long id, Model model) {
        Issue issue = issueService.findIssueById(id).orElse(null);
        if (issue == null) {
            return "redirect:/issues";
        }
        prepareModel(model);
        model.addAttribute("issue", issue);
        return "issue/add";
    }

    @PostMapping("/save")
    @Secured("ROLE_MANAGE_PROJECT")
    public String save(@Valid Issue issue, BindingResult result, Principal principal, Model model) {
        if (result.hasErrors()) {
            prepareModel(model);
            model.addAttribute("issue", issue);
            return "issue/add";
        }
        var optionalUser = issueService.getLoggedUser(principal);
        optionalUser.ifPresent(issue::setCreator);

        issueService.saveIssue(issue);
        return "redirect:/issues";
    }

    private void prepareModel(Model model){
        prepareModel(model, true);
    }

    private void prepareModel(Model model, Boolean modify) {
        if (modify) {
            model.addAttribute("assignees", issueService.findAllPersons());
        }
        model.addAttribute("projects", issueService.findAllProject());
        model.addAttribute("priorities", issueService.findAllPriorities());
        model.addAttribute("statuses", issueService.findAllStatuses());
        model.addAttribute("types", issueService.findAllTypes());
    }
}
