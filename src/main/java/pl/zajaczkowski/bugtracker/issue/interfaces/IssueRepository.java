package pl.zajaczkowski.bugtracker.issue.interfaces;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import pl.zajaczkowski.bugtracker.issue.Issue;
import pl.zajaczkowski.bugtracker.project.Project;

public interface IssueRepository extends CrudRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {

    Iterable<Issue> findAllByProject(Project project);
}
