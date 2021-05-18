package pl.zajaczkowski.bugtracker.issue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import pl.zajaczkowski.bugtracker.project.Project;

@Getter
@Setter
@NoArgsConstructor
public class IssueFilter {

    private String name;
    private String code;
    private Project project;
    private Status status;
    private Priority priority;
    private Type type;

    private Specification<Issue> hasStatus() {
        return (issueRoot, query, builder) -> builder.equal(issueRoot.get("status"), status);
    }

    private Specification<Issue> hasPriority() {
        return (issueRoot, query, builder) -> builder.equal(issueRoot.get("priority"), priority);
    }

    private Specification<Issue> hasType() {
        return (issueRoot, query, builder) -> builder.equal(issueRoot.get("type"), type);
    }

    private Specification<Issue> hasProject() {
        return (issueRoot, query, builder) -> builder.equal(issueRoot.get("project"), project);
    }

    private Specification<Issue> hasName() {
        return (issueRoot, query, builder) -> builder.like(builder.lower(issueRoot.get("name")),
                "%" + name.toLowerCase() + "%");
    }

    private Specification<Issue> hasCode() {
        return (issueRoot, query, builder) -> builder.like(builder.lower(issueRoot.get("code")),
                "%" + code.toLowerCase() + "%");
    }

    public Specification<Issue> buildQuery() {
        Specification<Issue> specification = Specification.where(null);

        if (name != null) {
            specification = specification.and(hasName());
        }

        if (code != null) {
            specification = specification.and(hasCode());
        }

        if (project != null) {
            specification = specification.and(hasProject());
        }

        if (priority != null) {
            specification = specification.and(hasPriority());
        }

        if (status != null) {
            specification = specification.and(hasStatus());
        }

        if (type != null) {
            specification = specification.and(hasType());
        }

        return specification;
    }
}
