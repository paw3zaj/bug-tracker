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

        if (personRepository.findByLogin(defaultAdminName) != null) {
            LOG.info("An administrator with the name '{}' already exist.", defaultAdminName);
            return; }

        LOG.info("Create an administrator: {}", defaultAdminName);

        Person admin = new Person(defaultAdminName, defaultAdminPassword, "Administrator");
        List<Authority> authorities = (List<Authority>) authorityRepository.findAll();
        admin.setAuthorities(new HashSet<>(authorities));

        savePerson(admin);
    }

    void savePerson(Person person) {
        String hashedPassword = bCryptPasswordEncoder.encode(person.getPassword());
        person.setPassword(hashedPassword);

        personRepository.save(person);
    }

    Iterable<Person> findAllPersons() {
        return personRepository.findAllByEnabledIsTrueAndLoginNotLike(defaultAdminName);
    }

    void disabledPerson(Long id){
        Optional<Person> optionalPerson = personRepository.findById(id);
        optionalPerson.ifPresent(person -> {
            person.isDisabled();
            personRepository.save(person);
        });
    }
}
