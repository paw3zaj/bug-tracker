package pl.zajaczkowski.bugtracker.issue;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class IssueModelAssembler implements RepresentationModelAssembler<Issue, EntityModel<Issue>> {

    @Override
    public EntityModel<Issue> toModel(Issue issue) {
        return EntityModel.of(issue,
                linkTo(methodOn(IssueRESTController.class).showIssue(issue.getId())).withSelfRel(),
                linkTo(methodOn(IssueRESTController.class).showAll()).withRel("issues"));
    }
}
