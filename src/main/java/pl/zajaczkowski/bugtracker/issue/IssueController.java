package pl.zajaczkowski.bugtracker.issue;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping
    String showIssueList(Model model){
        Iterable<Issue> issues = issueService.findAll();
        model.addAttribute("issues", issues);
        return "issue/show";
    }
}
