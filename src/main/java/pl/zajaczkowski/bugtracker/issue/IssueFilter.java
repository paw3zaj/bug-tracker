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

    private String globalSearch;
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

    private Specification<Issue> globalSearching() {

        Specification<Issue> hasName = (issueRoot, query, builder) -> builder.like(builder.lower(issueRoot.get("name")), "%" + globalSearch.toLowerCase() + "%");
        Specification<Issue> hasCode = (issueRoot, query, builder) -> builder.like(builder.lower(issueRoot.get("code")), "%" + globalSearch.toLowerCase() + "%");

        return hasName.or(hasCode);
    }

    public Specification<Issue> buildQuery() {
        Specification<Issue> specification = Specification.where(null);

        if (globalSearch != null) {
            specification = specification.and(globalSearching());
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
