package pl.zajaczkowski.bugtracker.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.zajaczkowski.bugtracker.auth.interfaces.AuthorityRepository;
import pl.zajaczkowski.bugtracker.auth.interfaces.PersonRepository;

import java.util.HashSet;
import java.util.List;

@Service
public class PersonService {

    @Value("${default.admin.name}")
    private String defaultAdminName;
    @Value("${default.admin.password}")
    private String defaultAdminPassword;

    private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

    private final PersonRepository personRepository;
    private final AuthorityRepository authorityRepository;

    public PersonService(PersonRepository personRepository, AuthorityRepository authorityRepository) {
        this.personRepository = personRepository;
        this.authorityRepository = authorityRepository;
    }

    public void prepareAdminUser() {

        if (personRepository.findByLogin(defaultAdminName) != null) {
            LOG.info("An administrator with the name '{}' already exist.", defaultAdminName);
            return; }

        LOG.info("Create an administrator: {}", defaultAdminName);

        Person admin = new Person(defaultAdminName, defaultAdminPassword, "Administrator");
        List<Authority> authorities = (List<Authority>) authorityRepository.findAll();
        admin.setAuthorities(new HashSet<>(authorities));

        savePerson(admin);
    }

    private void savePerson(Person person) {
        personRepository.save(person);
    }
}
