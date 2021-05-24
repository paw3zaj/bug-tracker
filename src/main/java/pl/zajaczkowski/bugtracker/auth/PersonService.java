package pl.zajaczkowski.bugtracker.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zajaczkowski.bugtracker.auth.interfaces.AuthorityRepository;
import pl.zajaczkowski.bugtracker.auth.interfaces.PersonRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${default.admin.name}")
    private String defaultAdminName;
    @Value("${default.admin.password}")
    private String defaultAdminPassword;

    private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

    public PersonService(PersonRepository personRepository, AuthorityRepository authorityRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.personRepository = personRepository;
        this.authorityRepository = authorityRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void prepareAdminUser() {
        Optional<Person> optionalPerson = findPersonByLogin(defaultAdminName);
        optionalPerson.ifPresentOrElse(person ->
                        LOG.info("An administrator with the login name '{}' already exist.", person.getLogin()),
                this::createDefaultAdminUser
        );
    }

    private void createDefaultAdminUser() {
        LOG.info("Create an administrator: {}", defaultAdminName);
        Person admin = new Person(defaultAdminName, defaultAdminPassword, "Administrator");
        List<Authority> authorities = (List<Authority>) findAllAuthorities();
        admin.setAuthorities(new HashSet<>(authorities));
        savePerson(admin);
    }

    void savePerson(Person person) {
        String hashedPassword = bCryptPasswordEncoder.encode(person.getPassword());
        person.setPassword(hashedPassword);

        personRepository.save(person);
    }

    public Iterable<Person> findAllPersons() {
        return personRepository.findAllByEnabledIsTrueAndLoginNotLike(defaultAdminName);
    }

    Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    void disabledPerson(Long id) {
        Optional<Person> optionalPerson = findById(id);
        optionalPerson.ifPresent(person -> {
            person.isDisabled();
            personRepository.save(person);
        });
    }

    public Optional<Person> findPersonByLogin(String name) {
        return personRepository.findByLogin(name);
    }

    Iterable<Authority> findAllAuthorities() {
        return authorityRepository.findAll();
    }
}
