package pl.zajaczkowski.bugtracker.issue;

import org.springframework.stereotype.Service;
import pl.zajaczkowski.bugtracker.issue.interfaces.IssueRepository;

@Service
public class IssueService {

    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    Iterable<Issue> findAll() {
        return issueRepository.findAll();
    }

    public Iterable<Issue> findAllIssues(IssueFilter issueFilter) {
        return issueRepository.findAll(issueFilter.buildQuery());
    }

    Optional<Issue> findIssueById(Long id) {
        return issueRepository.findById(id);
    }
}
