package pl.zajaczkowski.bugtracker.issue;

import org.springframework.stereotype.Service;
import pl.zajaczkowski.bugtracker.auth.Authority;
import pl.zajaczkowski.bugtracker.auth.AuthorityName;
import pl.zajaczkowski.bugtracker.auth.Person;
import pl.zajaczkowski.bugtracker.auth.PersonService;
import pl.zajaczkowski.bugtracker.auth.interfaces.AuthorityRepository;
import pl.zajaczkowski.bugtracker.auth.interfaces.PersonRepository;
import pl.zajaczkowski.bugtracker.issue.interfaces.IssueRepository;
import pl.zajaczkowski.bugtracker.issue.interfaces.PriorityRepository;
import pl.zajaczkowski.bugtracker.issue.interfaces.StatusRepository;
import pl.zajaczkowski.bugtracker.issue.interfaces.TypeRepository;
import pl.zajaczkowski.bugtracker.project.Project;
import pl.zajaczkowski.bugtracker.project.interfaces.ProjectRepository;

import java.util.Optional;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final PriorityRepository priorityRepository;
    private final StatusRepository statusRepository;
    private final TypeRepository typeRepository;
    private final PersonRepository personRepository;
    private final ProjectRepository projectRepository;
    private final AuthorityRepository authorityRepository;
    private final PersonService personService;

    public IssueService(IssueRepository issueRepository, PriorityRepository priorityRepository,
                        StatusRepository statusRepository, TypeRepository typeRepository,
                        PersonRepository personRepository, ProjectRepository projectRepository,
                        AuthorityRepository authorityRepository, PersonService personService) {
        this.issueRepository = issueRepository;
        this.priorityRepository = priorityRepository;
        this.statusRepository = statusRepository;
        this.typeRepository = typeRepository;
        this.personRepository = personRepository;
        this.projectRepository = projectRepository;
        this.authorityRepository = authorityRepository;
        this.personService = personService;
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

    Issue saveIssue(Issue issue){
        return issueRepository.save(issue);
    }

    Iterable<Person> findAllManagers(AuthorityName name) {
        Optional<Authority> authority = authorityRepository.findByName(name);
        if(authority.isEmpty()){
            return null;
        }
        return personRepository
                .findAllByEnabledIsTrueAndAuthoritiesContains(authority.get());
    }

    Iterable<Person> findAllPersons() {
        return personRepository
                .findAllByEnabledIsTrueAndLoginNotLike(
                        personService.getDefaultAdminName());
    }

    Iterable<Project> findAllProject() {
        return projectRepository.findAll();
    }

}
