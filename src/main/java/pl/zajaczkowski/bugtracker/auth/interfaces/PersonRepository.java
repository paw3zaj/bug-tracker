package pl.zajaczkowski.bugtracker.auth.interfaces;

import org.springframework.data.repository.CrudRepository;
import pl.zajaczkowski.bugtracker.auth.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

    Person findByLogin(String login);
}
