package pl.zajaczkowski.bugtracker.configuration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import pl.zajaczkowski.bugtracker.auth.Authority;
import pl.zajaczkowski.bugtracker.auth.AuthorityName;
import pl.zajaczkowski.bugtracker.auth.PersonService;
import pl.zajaczkowski.bugtracker.auth.interfaces.AuthorityRepository;

@Service
public class InitComponent implements InitializingBean {

    private final AuthorityRepository authorityRepository;
    private final PersonService personService;

    public InitComponent(AuthorityRepository authorityRepository, PersonService personService) {
        this.authorityRepository = authorityRepository;
        this.personService = personService;
    }

    @Override
    public void afterPropertiesSet() {

        prepareAuthorities();
        personService.prepareAdminUser();
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
}
