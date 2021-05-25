package pl.zajaczkowski.bugtracker.issue;

import org.springframework.stereotype.Service;
import pl.zajaczkowski.bugtracker.issue.interfaces.IssueRepository;
import pl.zajaczkowski.bugtracker.issue.interfaces.PriorityRepository;
import pl.zajaczkowski.bugtracker.issue.interfaces.StatusRepository;
import pl.zajaczkowski.bugtracker.issue.interfaces.TypeRepository;
import pl.zajaczkowski.bugtracker.mail.Mail;
import pl.zajaczkowski.bugtracker.project.Project;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final PriorityRepository priorityRepository;
    private final StatusRepository statusRepository;
    private final TypeRepository typeRepository;

    public IssueService(IssueRepository issueRepository, PriorityRepository priorityRepository,
                        StatusRepository statusRepository, TypeRepository typeRepository) {
        this.issueRepository = issueRepository;
        this.priorityRepository = priorityRepository;
        this.statusRepository = statusRepository;
        this.typeRepository = typeRepository;
    }

    Iterable<Priority> findAllPriorities() {
        return priorityRepository.findAll();
    }

    Iterable<Status> findAllStatuses() {
        return statusRepository.findAll();
    }

    Iterable<Type> findAllTypes(){
        return typeRepository.findAll();
    }

    void saveIssue(Issue issue){
        issueRepository.save(issue);
    }

    void deleteIssue(Long id) {
        issueRepository.deleteById(id);
    }

    public void deleteIssuesList(Iterable<Issue> issues) {
        issueRepository.deleteAll(issues);
    }

    public Iterable<Issue> findAllIssues(IssueFilter issueFilter) {
        return issueRepository.findAll(issueFilter.buildQuery());
    }

    public Iterable<Issue> findAllIssuesByProject(Project project) {
        return issueRepository.findAllByProject(project);
    }

    Optional<Issue> findIssueById(Long id) {
        return issueRepository.findById(id);
    }

    Mail prepareMail(Issue issue) {

        var dateCreated = issue.getDateCreated();
        var end = LocalDate.now();
        var duration = dateCreated.until(end, ChronoUnit.DAYS);

        var locale = Locale.getDefault();
        var bundle = ResourceBundle.getBundle("mail", locale);

        var subject = bundle.getString("subject");
        var content = bundle.getString("content");

        var subjectFormat = MessageFormat.format(subject, issue.getName());
        var contentFormat = MessageFormat.format(content, duration);

        return new Mail(subjectFormat, contentFormat);
    }
}
