package pl.zajaczkowski.bugtracker.configuration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import pl.zajaczkowski.bugtracker.auth.Authority;
import pl.zajaczkowski.bugtracker.auth.AuthorityName;
import pl.zajaczkowski.bugtracker.auth.PersonService;
import pl.zajaczkowski.bugtracker.auth.interfaces.AuthorityRepository;
import pl.zajaczkowski.bugtracker.issue.Priority;
import pl.zajaczkowski.bugtracker.issue.Status;
import pl.zajaczkowski.bugtracker.issue.Type;
import pl.zajaczkowski.bugtracker.issue.enumes.PriorityName;
import pl.zajaczkowski.bugtracker.issue.enumes.StatusName;
import pl.zajaczkowski.bugtracker.issue.enumes.TypeName;
import pl.zajaczkowski.bugtracker.issue.interfaces.PriorityRepository;
import pl.zajaczkowski.bugtracker.issue.interfaces.StatusRepository;
import pl.zajaczkowski.bugtracker.issue.interfaces.TypeRepository;

import java.util.Optional;

@Service
public class InitComponent implements InitializingBean {

    private final AuthorityRepository authorityRepository;
    private final PersonService personService;
    private final PriorityRepository priorityRepository;
    private final StatusRepository statusRepository;
    private final TypeRepository typeRepository;

    public InitComponent(AuthorityRepository authorityRepository, PersonService personService, PriorityRepository priorityRepository, StatusRepository statusRepository, TypeRepository typeRepository) {
        this.authorityRepository = authorityRepository;
        this.personService = personService;
        this.priorityRepository = priorityRepository;
        this.statusRepository = statusRepository;
        this.typeRepository = typeRepository;
    }

    @Override
    public void afterPropertiesSet() {

        prepareAuthorities();
        personService.prepareAdminUser();
        preparePriorities();
        prepareStatuses();
        prepareTypes();
    }

    private void prepareAuthorities() {
        for (AuthorityName name : AuthorityName.values()) {
            Authority existingAuthority = authorityRepository.findByName(name);
            if (existingAuthority == null) {
                Authority authority = new Authority(name);

                authorityRepository.save(authority);
            }
        }
    }

    private void preparePriorities() {
        for (PriorityName name : PriorityName.values()) {
            Optional<Priority> existingPriority = priorityRepository.findByName(name);
            if (existingPriority.isEmpty()) {
                Priority priority = new Priority(name);
                priorityRepository.save(priority);
            }
        }
    }

    private void prepareStatuses() {
        for (StatusName name : StatusName.values()) {
            Optional<Status> existingStatus = statusRepository.findByName(name);
            if (existingStatus.isEmpty()) {
                Status status = new Status(name);
                statusRepository.save(status);
            }
        }
    }

    private void prepareTypes() {
        for (TypeName name : TypeName.values()) {
            Optional<Type> existingType = typeRepository.findByName(name);
            if (existingType.isEmpty()) {
                Type type = new Type(name);
                typeRepository.save(type);
            }
        }
    }
}
