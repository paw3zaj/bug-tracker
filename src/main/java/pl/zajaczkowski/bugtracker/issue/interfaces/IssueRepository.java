package pl.zajaczkowski.bugtracker.issue.interfaces;

import org.springframework.data.repository.CrudRepository;
import pl.zajaczkowski.bugtracker.issue.Issue;

public interface IssueRepository extends CrudRepository<Issue, Long> {
}
