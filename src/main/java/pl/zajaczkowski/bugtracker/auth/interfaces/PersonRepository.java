package pl.zajaczkowski.bugtracker.auth.interfaces;

import org.springframework.data.repository.CrudRepository;
import pl.zajaczkowski.bugtracker.auth.Person;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {

    Optional<Person> findByLogin(String login);
    Iterable<Person> findAllByEnabledIsTrueAndLoginNotLike(String superAdmin);
}
