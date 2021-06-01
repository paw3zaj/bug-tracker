package pl.zajaczkowski.bugtracker.issue;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Secured("ROLE_MANAGE_PROJECT")
@RestController
@RequestMapping("/api/issues")
public class IssueRESTController {

    private final IssueService issueService;
    private final IssueModelAssembler assembler;

    public IssueRESTController(IssueService issueService, IssueModelAssembler assembler) {
        this.issueService = issueService;
        this.assembler = assembler;
    }


    @GetMapping
    public CollectionModel<EntityModel<Issue>> showAll() {
        List<Issue> issueList = (List<Issue>) issueService.findAllIssues();

        List<EntityModel<Issue>> issues = issueList.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(issues,
                linkTo(methodOn(IssueRESTController.class).showAll()).withSelfRel());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> showIssue(@PathVariable Long id) {
        var issue = issueService.findIssueById(id);

        if (issue.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Issue> issueModel = assembler.toModel(issue.get());

        return ResponseEntity.ok().body(issueModel);
    }
}
